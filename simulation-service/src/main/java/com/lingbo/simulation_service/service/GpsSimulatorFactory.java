package com.lingbo.simulation_service.service;


import java.io.File;
import java.util.List;

import com.lingbo.simulation_service.model.GpsSimulatorRequest;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.task.GpsSimulator;

public interface GpsSimulatorFactory {

    GpsSimulator prepareGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest);

    GpsSimulator prepareGpsSimulator(GpsSimulator gpsSimulator, File kmlFile);

    GpsSimulator prepareGpsSimulator(GpsSimulator gpsSimulator, List<Point> points);

}