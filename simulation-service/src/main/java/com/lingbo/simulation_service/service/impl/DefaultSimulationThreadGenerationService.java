package com.lingbo.simulation_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingbo.simulation_service.model.SimulationThreadRequest;
import com.lingbo.simulation_service.model.Leg;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.service.SimulationThreadGenerationService;
import com.lingbo.simulation_service.service.PathService;
import com.lingbo.simulation_service.service.NotificationService;
import com.lingbo.simulation_service.support.NavUtils;
import com.lingbo.simulation_service.task.SimulationThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 */
@Service
public class DefaultSimulationThreadGenerationService implements SimulationThreadGenerationService {

    @Autowired
    private PathService pathService;

    @Autowired
    private NotificationService notificationService;

    private final AtomicLong instanceCounter = new AtomicLong();

    @Override
    public SimulationThread create(SimulationThreadRequest simulationThreadRequest) {

        final SimulationThread simulationThread = new SimulationThread(simulationThreadRequest);

        simulationThread.setPositionInfoService(notificationService);
        simulationThread.setId(this.instanceCounter.incrementAndGet());

        final List<Point> points = NavUtils.decodePolyline(simulationThreadRequest.getPolyline());
        simulationThread.setStartPoint(points.iterator().next());

        return create(simulationThread, points);
    }

    @Override
    public SimulationThread create(SimulationThread simulationThread, File kmlFile) {

        final List<Point> points;

        if (kmlFile == null) {
            points = this.pathService.getCoordinatesFromGoogle(this.pathService.loadDirectionInput().get(0));
        } else {
//            points = this.pathService.getCoordinatesFromKmlFile(kmlFile);
            points = new ArrayList<>();
        }

        return create(simulationThread, points);
    }

    @Override
    public SimulationThread create(SimulationThread simulationThread, List<Point> points) {
    	simulationThread.setCurrentPosition(null);

        final List<Leg> legs = createLegsList(points);
        simulationThread.setLegs(legs);
        simulationThread.setStartPosition();
        return simulationThread;
    }

    /**
     * Creates list of legs in the path
     *
     * @param points
     */
    private List<Leg> createLegsList(List<Point> points) {
        final List<Leg> legs = new ArrayList<Leg>();
        for (int i = 0; i < (points.size() - 1); i++) {
            Leg leg = new Leg();
            leg.setId(i);
            leg.setStartPosition(points.get(i));
            leg.setEndPosition(points.get(i + 1));
            Double length = NavUtils.getDistance(points.get(i), points.get(i + 1));
            leg.setLength(length);
            Double heading = NavUtils.getBearing(points.get(i), points.get(i + 1));
            leg.setHeading(heading);
            legs.add(leg);
        }
        return legs;
    }


}