package com.lingbo.simulation_service.model;


public class SimulationThreadRequest {

    private String vin;
    private Double speedInKph;
    private boolean move = true;
    private boolean exportPositionsToKml = false;
    private boolean exportPositionsToMessaging = true;
    private Integer reportInterval = 500;
    private int secondsToError = 0;
    private VehicleStatus vehicleStatus = VehicleStatus.NONE;
    private String polyline;
    private FaultCode faultCode;

    public Double getSpeedInKph() {
        return speedInKph;
    }

    public void setSpeedInKph(Double speedInKph) {
        this.speedInKph = speedInKph;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isExportPositionsToKml() {
        return exportPositionsToKml;
    }

    public void setExportPositionsToKml(boolean exportPositionsToKml) {
        this.exportPositionsToKml = exportPositionsToKml;
    }

    public boolean isExportPositionsToMessaging() {
        return exportPositionsToMessaging;
    }

    public void setExportPositionsToMessaging(boolean exportPositionsToMessaging) {
        this.exportPositionsToMessaging = exportPositionsToMessaging;
    }

    public Integer getReportInterval() {
        return reportInterval;
    }

    public void setReportInterval(Integer reportInterval) {
        this.reportInterval = reportInterval;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public int getSecondsToError() {
        return secondsToError;
    }

    public void setSecondsToError(int secondsToError) {
        this.secondsToError = secondsToError;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "SimulationThreadRequest [vin=" + vin + ", speedInKph=" + speedInKph + ", move=" + move + ", exportPositionsToKml="
                + exportPositionsToKml + ", exportPositionsToMessaging=" + exportPositionsToMessaging
                + ", reportInterval=" + reportInterval + "]";
    }

    public void setFaultCode(FaultCode faultCode) {
        this.faultCode = faultCode;
    }

    public FaultCode getFaultCode() {
        return faultCode;
    }

}
