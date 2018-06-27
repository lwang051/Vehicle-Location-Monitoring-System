package com.lingbo.simulation_service.service;


import java.io.File;
import java.util.List;

import com.lingbo.simulation_service.model.GpsSimulatorRequest;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.task.GpsSimulator;

public interface GpsSimulatorGenerationService {

    GpsSimulator createGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest);

    GpsSimulator createGpsSimulator(GpsSimulator gpsSimulator, File kmlFile);

    GpsSimulator createGpsSimulator(GpsSimulator gpsSimulator, List<Point> points);

}