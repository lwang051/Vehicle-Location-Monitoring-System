package com.lingbo.simulation_service.service;



import java.io.File;
import java.util.List;

import com.lingbo.simulation_service.domain.Point;
import com.lingbo.simulation_service.domain.SimulatorRequest;
import com.lingbo.simulation_service.task.Simulator;


public interface SimulatorFactory {
	
	Simulator prepareGpsSimulator(SimulatorRequest simulatorRequest);
    Simulator prepareGpsSimulator(Simulator simulator, File kmlFile);
    Simulator prepareGpsSimulator(Simulator simulator, List<Point> points);
	
}
