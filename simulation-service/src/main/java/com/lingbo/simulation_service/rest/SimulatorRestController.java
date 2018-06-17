package com.lingbo.simulation_service.rest;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lingbo.simulation_service.domain.Direction;
import com.lingbo.simulation_service.domain.Point;
import com.lingbo.simulation_service.domain.ServiceLocation;
import com.lingbo.simulation_service.domain.SimulatorFixture;
import com.lingbo.simulation_service.domain.SimulatorRequest;
import com.lingbo.simulation_service.domain.VehicleStatus;
import com.lingbo.simulation_service.service.PathService;
import com.lingbo.simulation_service.service.SimulatorFactory;
import com.lingbo.simulation_service.support.FaultCodeUtils;
import com.lingbo.simulation_service.task.Simulator;
import com.lingbo.simulation_service.task.SimulatorInstance;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/simulator")
public class SimulatorRestController {
	
	@Autowired
    private PathService pathService;

//    @Autowired
//    private KmlService kmlService;

    @Autowired
    private SimulatorFactory simulatorFactory;

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    private Map<Long, SimulatorInstance> taskFutures = new HashMap<Long, SimulatorInstance>();

    @RequestMapping("/dc")
    public List<SimulatorInstance> dc(HttpServletRequest request) {
        final SimulatorFixture fixture = this.pathService.loadSimulatorFixture();

        final List<SimulatorInstance> instances = new ArrayList<SimulatorInstance>();
        final List<Point> lookAtPoints = new ArrayList<Point>();

        final Set<Long> instanceIds = new HashSet<Long>(taskFutures.keySet());

        for (SimulatorRequest simulatorRequest : fixture.getSimulatorRequests()) {

            final Simulator simulator = simulatorFactory.prepareGpsSimulator(simulatorRequest);
            lookAtPoints.add(simulator.getStartPoint());
            instanceIds.add(simulator.getId());

            final Future<?> future = taskExecutor.submit(simulator);
            final SimulatorInstance instance = new SimulatorInstance(simulator.getId(), simulator, future);
            taskFutures.put(simulator.getId(), instance);
            instances.add(instance);
        }

//        if (fixture.usesKmlIntegration()) {
//            kmlService.setupKmlIntegration(instanceIds, NavUtils.getLookAtPoint(lookAtPoints), getKmlUrl(request));
//        }

        return instances;
    }

    @RequestMapping("/status")
    public Collection<SimulatorInstance> status() {
        return taskFutures.values();
    }

    @RequestMapping("/cancel")
    public int cancel() {
        int numberOfCancelledTasks = 0;
        for (Map.Entry<Long, SimulatorInstance> entry : taskFutures.entrySet()) {
            SimulatorInstance instance = entry.getValue();
            instance.getSimulator().cancel();
            boolean wasCancelled = instance.getSimulatorTask().cancel(true);
            if (wasCancelled) {
                numberOfCancelledTasks++;
            }
        }
        taskFutures.clear();
//        this.kmlService.clearKmlInstances();
        return numberOfCancelledTasks;
    }

    @RequestMapping("/directions")
    public List<Direction> directions() {
        return pathService.loadDirection();
    }

    @RequestMapping("/service-locations")
    public List<ServiceLocation> serviceLocations() {
        return pathService.getServiceStations();
    }

    @RequestMapping("/fixture")
    public SimulatorFixture fixture() {

        final List<Direction> directions = this.pathService.loadDirection();
        final SimulatorFixture fixture = new SimulatorFixture();

        for (Direction directionInput : directions) {

            final SimulatorRequest gpsSimulatorRequest = new SimulatorRequest();
            gpsSimulatorRequest.setExportPositionsToKml(true);
            gpsSimulatorRequest.setExportPositionsToMessaging(true);
            gpsSimulatorRequest.setMove(true);

            String polyline = this.pathService.getCoordinatesFromGoogleAsPolyline(directionInput);
            gpsSimulatorRequest.setPolyline(polyline);
            gpsSimulatorRequest.setReportInterval(1000);
            gpsSimulatorRequest.setSpeedInKph(50d);
            gpsSimulatorRequest.setExportPositionsToMessaging(true);
            gpsSimulatorRequest.setSecondsToError(60);
            gpsSimulatorRequest.setVehicleStatus(VehicleStatus.NONE);
            gpsSimulatorRequest.setFaultCode(FaultCodeUtils.getRandomFaultCode());
            fixture.getSimulatorRequests().add(gpsSimulatorRequest);
        }

        return fixture;
    }

//    @RequestMapping("/kml/{instanceId}")
//    public byte[] getKmlInstance(@PathVariable Long instanceId) {
//        return kmlService.getKmlInstance(instanceId);
//    }

//    @RequestMapping("/gps.kml")
//    public byte[] getKmlBootstrapKml() {
//        return kmlService.getKmlBootstrap();
//    }
    
    
    @SuppressWarnings("unused")
    private String getKmlUrl(HttpServletRequest request) {

        final String scheme = request.getScheme();
        final String serverName = request.getServerName();
        final int serverPort = request.getServerPort();
        final String contextPath = request.getContextPath();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append("/api/kml/");
        return url.toString();
    }
	
}
