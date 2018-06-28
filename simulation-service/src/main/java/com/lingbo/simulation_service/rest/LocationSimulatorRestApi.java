package com.lingbo.simulation_service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lingbo.simulation_service.model.GpsSimulatorRequest;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.model.SimulatorFixture;
import com.lingbo.simulation_service.service.GpsSimulatorGenerationService;
import com.lingbo.simulation_service.service.PathService;
import com.lingbo.simulation_service.task.GpsSimulator;
import com.lingbo.simulation_service.task.GpsSimulatorInstance;

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
@RequestMapping("/api")
public class LocationSimulatorRestApi {

    @Autowired
    private PathService pathService;
    @Autowired
    private GpsSimulatorGenerationService gpsSimulatorGenerationService;
    @Autowired
    private AsyncTaskExecutor taskExecutor;
    private Map<Long, GpsSimulatorInstance> taskFutures = new HashMap<>();

    @RequestMapping("/dc")
    public List<GpsSimulatorInstance> dc(HttpServletRequest request) {
        final SimulatorFixture fixture = this.pathService.loadSimulatorFixture();
        final List<GpsSimulatorInstance> instances = new ArrayList<>();
        final List<Point> lookAtPoints = new ArrayList<>();
        final Set<Long> instanceIds = new HashSet<>(taskFutures.keySet());
        for (GpsSimulatorRequest gpsSimulatorRequest : fixture.getGpsSimulatorRequests()) {
            final GpsSimulator gpsSimulator = gpsSimulatorGenerationService.createGpsSimulator(gpsSimulatorRequest);
            lookAtPoints.add(gpsSimulator.getStartPoint());
            instanceIds.add(gpsSimulator.getId());
            final Future<?> future = taskExecutor.submit(gpsSimulator);
            final GpsSimulatorInstance instance = new GpsSimulatorInstance(gpsSimulator.getId(), gpsSimulator, future);
            taskFutures.put(gpsSimulator.getId(), instance);
            instances.add(instance);
        }
        return instances;
    }

    @RequestMapping("/status")
    public Collection<GpsSimulatorInstance> status() {
        return taskFutures.values();
    }

    @RequestMapping("/cancel")
    public int cancel() {
        int numberOfCancelledTasks = 0;
        for (Map.Entry<Long, GpsSimulatorInstance> entry : taskFutures.entrySet()) {
            GpsSimulatorInstance instance = entry.getValue();
            instance.getGpsSimulator().cancel();
            boolean wasCancelled = instance.getGpsSimulatorTask().cancel(true);
            if (wasCancelled) {
                numberOfCancelledTasks++;
            }
        }
        taskFutures.clear();
        return numberOfCancelledTasks;
    }
    
}
