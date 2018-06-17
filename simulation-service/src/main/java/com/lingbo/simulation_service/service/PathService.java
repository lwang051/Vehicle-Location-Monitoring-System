package com.lingbo.simulation_service.service;

import java.util.List;

import com.lingbo.simulation_service.model.DirectionInput;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.model.ServiceLocation;
import com.lingbo.simulation_service.model.SimulatorFixture;

/**
 *
 *
 *
 */
public interface PathService {

    /**
     * @return
     */
    List<DirectionInput> loadDirectionInput();

    SimulatorFixture loadSimulatorFixture();

    /**
     * @param directionInput
     * @return
     */
    List<Point> getCoordinatesFromGoogle(DirectionInput directionInput);

    String getCoordinatesFromGoogleAsPolyline(DirectionInput directionInput);


//    List<Point> getCoordinatesFromKmlFile(File kmlFile);

    List<ServiceLocation> getServiceStations();
}