package com.lingbo.simulation_service.service.impl;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingbo.simulation_service.domain.Leg;
import com.lingbo.simulation_service.domain.Point;
import com.lingbo.simulation_service.domain.SimulatorRequest;
import com.lingbo.simulation_service.service.PathService;
import com.lingbo.simulation_service.service.PositionService;
import com.lingbo.simulation_service.service.SimulatorFactory;
import com.lingbo.simulation_service.support.NavUtils;
import com.lingbo.simulation_service.task.Simulator;

@Service
public class DefaultSimulatorFactory implements SimulatorFactory {
	
	@Autowired
    private PathService pathService;

    @Autowired
    private PositionService positionService;

    private final AtomicLong instanceCounter = new AtomicLong();

    @Override
    public Simulator prepareGpsSimulator(SimulatorRequest simulatorRequest) {

        final Simulator simulator = new Simulator(simulatorRequest);

        simulator.setPositionInfoService(positionService);
        simulator.setId(this.instanceCounter.incrementAndGet());

        final List<Point> points = NavUtils.decodePolyline(simulatorRequest.getPolyline());
        simulator.setStartPoint(points.iterator().next());

        return prepareGpsSimulator(simulator, points);
    }

    @Override
    public Simulator prepareGpsSimulator(Simulator simulator, File kmlFile) {

        final List<Point> points;

        if (kmlFile == null) {
            points = this.pathService.getCoordinatesFromGoogle(this.pathService.loadDirection().get(0));
        } else {
//            points = this.pathService.getCoordinatesFromKmlFile(kmlFile);
            points = new ArrayList<Point>();
        }

        return prepareGpsSimulator(simulator, points);
    }

    @Override
    public Simulator prepareGpsSimulator(Simulator gpsSimulator, List<Point> points) {
        gpsSimulator.setCurrentPosition(null);

        final List<Leg> legs = createLegsList(points);
        gpsSimulator.setLegs(legs);
        gpsSimulator.setStartPosition();
        return gpsSimulator;
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
            leg.setStart(points.get(i));
            leg.setEnd(points.get(i + 1));
            Double length = NavUtils.getDistance(points.get(i), points.get(i + 1));
            leg.setLength(length);
            Double heading = NavUtils.getBearing(points.get(i), points.get(i + 1));
            leg.setHeading(heading);
            legs.add(leg);
        }
        return legs;
    }
	
}
