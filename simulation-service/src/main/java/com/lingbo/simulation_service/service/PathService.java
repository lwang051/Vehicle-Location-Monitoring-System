package com.lingbo.simulation_service.service;



import java.util.List;

import com.lingbo.simulation_service.domain.Direction;
import com.lingbo.simulation_service.domain.Point;
import com.lingbo.simulation_service.domain.ServiceLocation;
import com.lingbo.simulation_service.domain.SimulatorFixture;

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
