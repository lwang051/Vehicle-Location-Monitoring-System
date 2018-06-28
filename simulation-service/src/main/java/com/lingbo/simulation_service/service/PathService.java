package com.lingbo.simulation_service.service;

import java.util.List;

import com.lingbo.simulation_service.model.DirectionInput;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.model.ServiceLocation;
import com.lingbo.simulation_service.model.SimulatorFixture;


public interface PathService {

    List<DirectionInput> loadDirectionInput();
    SimulatorFixture loadSimulatorFixture();
    List<Point> getCoordinatesFromGoogle(DirectionInput directionInput);
    String getCoordinatesFromGoogleAsPolyline(DirectionInput directionInput);
    List<ServiceLocation> getServiceStations();
    
}