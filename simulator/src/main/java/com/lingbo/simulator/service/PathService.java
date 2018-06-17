package com.lingbo.simulator.service;

import java.util.List;

import com.lingbo.simulator.domain.Direction;
import com.lingbo.simulator.domain.Point;
import com.lingbo.simulator.domain.ServiceLocation;
import com.lingbo.simulator.domain.SimulatorFixture;

public interface PathService {
	
	/**
     * @return
     */
    List<Direction> loadDirection();

    SimulatorFixture loadSimulatorFixture();

    /**
     * @param direction
     * @return
     */
    List<Point> getCoordinatesFromGoogle(Direction direction);

    String getCoordinatesFromGoogleAsPolyline(Direction direction);


//    List<Point> getCoordinatesFromKmlFile(File kmlFile);

    List<ServiceLocation> getServiceStations();
	
}
