package com.lingbo.simulation_service.service;


import java.io.File;
import java.util.List;

import com.lingbo.simulation_service.model.SimulationThreadRequest;
import com.lingbo.simulation_service.model.Point;
import com.lingbo.simulation_service.task.SimulationThread;

public interface SimulationThreadGenerationService {

    SimulationThread create(SimulationThreadRequest simulationThreadRequest);

    SimulationThread create(SimulationThread simulationThread, File kmlFile);

    SimulationThread create(SimulationThread simulationThread, List<Point> points);

}