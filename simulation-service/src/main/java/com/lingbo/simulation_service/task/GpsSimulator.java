package com.lingbo.simulation_service.task;


import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


import com.lingbo.simulation_service.model.CurrentPosition;
import com.lingbo.simulation_service.model.FaultCode;
import com.lingbo.simulation_service.model.GpsSimulatorRequest;
import com.lingbo.simulation_service.model.Leg;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.model.PositionInfo;
import com.lingbo.simulation_service.model.VehicleStatus;
import com.lingbo.simulation_service.service.NotificationService;
import com.lingbo.simulation_service.support.NavUtils;

/**
 * Simulates a vehicle moving along a path defined in a kml file.
 */
public class GpsSimulator implements Runnable {
    
    private long id;
    private NotificationService notificationService;
    private AtomicBoolean cancel = new AtomicBoolean();
    private Double speedInMps; // In meters/sec
    private boolean shouldMove;
    private boolean exportPositionsToKml = false;
    private boolean exportPositionsToMessaging = true;
    private Integer reportInterval = 500; // millisecs at which to send position reports
    private PositionInfo positionInfo = null;
    private List<Leg> legs;
    private VehicleStatus vehicleStatus = VehicleStatus.NONE;
    private String vin;
    private Integer secondsToError = 45;
    private Point startPoint;
    private Date executionStartTime;
    private FaultCode faultCode;

    public GpsSimulator(GpsSimulatorRequest gpsSimulatorRequest) {
        this.shouldMove = gpsSimulatorRequest.isMove();
        this.exportPositionsToKml = gpsSimulatorRequest.isExportPositionsToKml();
        this.exportPositionsToMessaging = gpsSimulatorRequest.isExportPositionsToMessaging();
        this.setSpeedInKph(gpsSimulatorRequest.getSpeedInKph());
        this.reportInterval = gpsSimulatorRequest.getReportInterval();
        this.secondsToError = gpsSimulatorRequest.getSecondsToError();
        this.vin = gpsSimulatorRequest.getVin();
        this.vehicleStatus = gpsSimulatorRequest.getVehicleStatus();
        this.faultCode = gpsSimulatorRequest.getFaultCode();
    }

    @Override
    public void run() {
        try {
            executionStartTime = new Date();
            if (cancel.get()) {
                destroy();
                return;
            }
            while (!Thread.interrupted()) {
                long startTime = new Date().getTime();
                if (positionInfo != null) { // has next position info
                    if (shouldMove) {
                    	updatePositionInfo();
                        positionInfo.setSpeed(speedInMps);
                    } else {
                        positionInfo.setSpeed(0d);
                    }
                    if (this.secondsToError > 0 && 
                    		startTime - executionStartTime.getTime() >= this.secondsToError * 1000) {
                        this.vehicleStatus = VehicleStatus.SERVICE_NOW;
                    }
                    positionInfo.setVehicleStatus(this.vehicleStatus);
                    final FaultCode faultCodeToUse;
                    switch (this.vehicleStatus) {
                        case SERVICE_INFO:
                        case SERVICE_SOON:
                        case STOP_NOW:
                            faultCodeToUse = this.faultCode;
                            break;
                        default:
                            faultCodeToUse = null;
                            break;
                    }
                    notificationService.send(id, 
                        new CurrentPosition(positionInfo.getVin(), 
                            new Point(positionInfo.getPosition().getLatitude(), 
                                positionInfo.getPosition().getLongitude()),
                            positionInfo.getVehicleStatus(),
                    		positionInfo.getSpeed(),
                    		positionInfo.getLeg().getHeading(),
                    		faultCodeToUse), 
                    	this.exportPositionsToKml, 
                    	this.exportPositionsToMessaging);
                }
                // wait till next position report is due
                sleepForTheRestTime(startTime);
            }
        } catch (InterruptedException ie) {
            destroy();
            return;
        }
        destroy();
    }

    /**
     * On thread interrupt. Send null position to all consumers to indicate that
     * sim has closed.
     */
    void destroy() {
        positionInfo = null;
    }

    /**
     * Sleep till next position report is due.
     *
     * @param startTime
     * @throws InterruptedException
     */
    private void sleepForTheRestTime(long startTime) throws InterruptedException {
        long elapsedTime = new Date().getTime() - startTime;
        long sleepTime = reportInterval - elapsedTime > 0 ? reportInterval - elapsedTime : 0;
        Thread.sleep(sleepTime);
    }

    /**
     * Set new position of vehicle based on current position and vehicle speed.
     */
    void updatePositionInfo() {
        Double distance = speedInMps * reportInterval / 1000.0;
        Double distanceFromStart = positionInfo.getDistanceFromStart() + distance;
        Double excess = 0.0; // amount by which next postion will exceed end

        for (int i = positionInfo.getLeg().getId(); i < legs.size(); i++) {
            Leg currentLeg = legs.get(i);
            excess = distanceFromStart > currentLeg.getLength() ? distanceFromStart - currentLeg.getLength() : 0.0;

            if (Double.doubleToRawLongBits(excess) == 0) { // position falls within current leg
                positionInfo.setDistanceFromStart(distanceFromStart);
                positionInfo.setLeg(currentLeg);
                Point newPosition = NavUtils.getPosition(currentLeg.getStartPosition(), distanceFromStart,
                        currentLeg.getHeading());
                positionInfo.setPosition(newPosition);
                return;
            }
            distanceFromStart = excess;
        }

        // if we've reached here it means vehicle has moved beyond end of path
        // so go back to start of path
        setStartPosition();
    }

    /**
     * Position vehicle at start of path.
     */
    public void setStartPosition() {
        positionInfo = new PositionInfo();
        positionInfo.setVin(this.vin);
        Leg leg = legs.get(0);
        positionInfo.setLeg(leg);
        positionInfo.setPosition(leg.getStartPosition());
        positionInfo.setDistanceFromStart(0.0);
    }

    /**
     * @return the speed
     */
    public Double getSpeedInMps() {
        return speedInMps;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeedInMps(Double speed) {
        this.speedInMps = speed;
    }

    public void setSpeedInKph(Double speed) {
        this.speedInMps = speed / 3.6;
    }

    public Double getSpeedInKph() {
        return this.speedInMps * 3.6;
    }

    /**
     * @return the shouldMove
     */
    public Boolean getShouldMove() {
        return shouldMove;
    }

    /**
     * @param shouldMove the shouldMove to set
     */
    public void setShouldMove(Boolean shouldMove) {
        this.shouldMove = shouldMove;
    }

    public synchronized void cancel() {
        this.cancel.set(true);
    }

    public void setExportPositionsToKml(boolean exportPositionsToKml) {
        this.exportPositionsToKml = exportPositionsToKml;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public PositionInfo getCurrentPosition() {
        return positionInfo;
    }

    public void setCurrentPosition(PositionInfo currentPosition) {
        this.positionInfo = currentPosition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public Integer getSecondsToError() {
        return secondsToError;
    }

    public void setSecondsToError(Integer secondsToError) {
        this.secondsToError = secondsToError;
    }

    public void setPositionInfoService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public String toString() {
        return "GpsSimulator [id=" + id + ", speedInMps=" + speedInMps + ", shouldMove=" + shouldMove
                + ", exportPositionsToKml=" + exportPositionsToKml + ", exportPositionsToMessaging="
                + exportPositionsToMessaging + ", reportInterval=" + reportInterval + ", currentPosition="
                + positionInfo + "]";
    }

}
