package com.lingbo.simulation_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lingbo.simulation_service.model.GpsSimulatorRequest;
import com.lingbo.simulation_service.model.Leg;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.service.GpsSimulatorGenerationService;
import com.lingbo.simulation_service.service.PathService;
import com.lingbo.simulation_service.service.PositionService;
import com.lingbo.simulation_service.support.NavUtils;
import com.lingbo.simulation_service.task.GpsSimulator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 */
@Service
public class DefaultGpsSimulatorGenerationService implements GpsSimulatorGenerationService {

    @Autowired
    private PathService pathService;

    @Autowired
    private PositionService positionService;

    private final AtomicLong instanceCounter = new AtomicLong();

    @Override
    public GpsSimulator createGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest) {

        final GpsSimulator gpsSimulator = new GpsSimulator(gpsSimulatorRequest);

        gpsSimulator.setPositionInfoService(positionService);
        gpsSimulator.setId(this.instanceCounter.incrementAndGet());

        final List<Point> points = NavUtils.decodePolyline(gpsSimulatorRequest.getPolyline());
        gpsSimulator.setStartPoint(points.iterator().next());

        return createGpsSimulator(gpsSimulator, points);
    }

    @Override
    public GpsSimulator createGpsSimulator(GpsSimulator gpsSimulator, File kmlFile) {

        final List<Point> points;

        if (kmlFile == null) {
            points = this.pathService.getCoordinatesFromGoogle(this.pathService.loadDirectionInput().get(0));
        } else {
//            points = this.pathService.getCoordinatesFromKmlFile(kmlFile);
            points = new ArrayList<>();
        }

        return createGpsSimulator(gpsSimulator, points);
    }

    @Override
    public GpsSimulator createGpsSimulator(GpsSimulator gpsSimulator, List<Point> points) {
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