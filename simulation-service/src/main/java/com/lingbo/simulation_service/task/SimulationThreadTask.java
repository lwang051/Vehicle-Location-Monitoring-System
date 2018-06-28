package com.lingbo.simulation_service.task;


import java.util.concurrent.Future;


public class SimulationThreadTask {

    private long id;
    private SimulationThread simulationThread;
    private Future<?> task;

    public SimulationThreadTask(long id, SimulationThread simulationThread, Future<?> task) {
        super();
        this.id = id;
        this.simulationThread = simulationThread;
        this.task = task;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SimulationThread getSimulationThread() {
        return simulationThread;
    }

    public void setSimulationThread(SimulationThread simulationThread) {
        this.simulationThread = simulationThread;
    }

    public Future<?> getTask() {
        return task;
    }

    public void setTask(Future<?> task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "SimulationThreadTask [id=" + id + ", simulationThread=" + simulationThread
                + ", task=" + task + "]";
    }

}
