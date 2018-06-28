package com.lingbo.simulation_service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lingbo.simulation_service.model.SimulationThreadRequest;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.model.SimulationFixture;
import com.lingbo.simulation_service.service.SimulationThreadGenerationService;
import com.lingbo.simulation_service.service.PathService;
import com.lingbo.simulation_service.task.SimulationThread;
import com.lingbo.simulation_service.task.SimulationThreadTask;

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
public class SimulationServiceRestApi {

    @Autowired
    private PathService pathService;
    @Autowired
    private SimulationThreadGenerationService simulationThreadGenerationService;
    @Autowired
    private AsyncTaskExecutor taskExecutor;
    private Map<Long, SimulationThreadTask> taskFutures = new HashMap<>();

    @RequestMapping("/dc")
    public List<SimulationThreadTask> dc(HttpServletRequest request) {
        final SimulationFixture fixture = this.pathService.loadSimulationFixture();
        final List<SimulationThreadTask> instances = new ArrayList<>();
        final List<Point> lookAtPoints = new ArrayList<>();
        final Set<Long> instanceIds = new HashSet<>(taskFutures.keySet());
        System.out.println("fixture size: " + fixture.getNumberOfSimulationThreadRequests());
        for (SimulationThreadRequest simulationThreadRequest : fixture.getSimulationThreadRequests()) {
            final SimulationThread simulationThread = simulationThreadGenerationService.create(simulationThreadRequest);
            lookAtPoints.add(simulationThread.getStartPoint());
            instanceIds.add(simulationThread.getId());
            final Future<?> future = taskExecutor.submit(simulationThread);
            final SimulationThreadTask instance = new SimulationThreadTask(simulationThread.getId(), simulationThread, future);
            taskFutures.put(simulationThread.getId(), instance);
            instances.add(instance);
        }
        return instances;
    }

    @RequestMapping("/status")
    public Collection<SimulationThreadTask> status() {
        return taskFutures.values();
    }

    @RequestMapping("/cancel")
    public int cancel() {
        int numberOfCancelledTasks = 0;
        for (Map.Entry<Long, SimulationThreadTask> entry : taskFutures.entrySet()) {
            SimulationThreadTask instance = entry.getValue();
            instance.getSimulationThread().cancel();
            boolean wasCancelled = instance.getTask().cancel(true);
            if (wasCancelled) {
                numberOfCancelledTasks++;
            }
        }
        taskFutures.clear();
        return numberOfCancelledTasks;
    }
    
}
