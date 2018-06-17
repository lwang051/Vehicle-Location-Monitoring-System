package com.lingbo.simulator.service;

import java.io.File;
import java.util.List;

import com.lingbo.simulator.domain.Point;
import com.lingbo.simulator.domain.SimulatorRequest;
import com.lingbo.simulator.task.Simulator;

public interface SimulatorFactory {
	
	Simulator prepareGpsSimulator(SimulatorRequest simulatorRequest);
    Simulator prepareGpsSimulator(Simulator simulator, File kmlFile);
    Simulator prepareGpsSimulator(Simulator simulator, List<Point> points);
	
}
