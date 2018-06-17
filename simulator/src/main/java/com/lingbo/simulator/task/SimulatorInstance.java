package com.lingbo.simulator.task;

import java.util.concurrent.Future;
import lombok.Data;

@Data
public class SimulatorInstance {
	private long instanceId;
    private Simulator simulator;
    private Future<?> simulatorTask;

    public SimulatorInstance(long instanceId, Simulator simulator, Future<?> simulatorTask) {
        super();
        this.instanceId = instanceId;
        this.simulator = simulator;
        this.simulatorTask = simulatorTask;
    }

    @Override
    public String toString() {
        return "SimulatorInstance [instanceId=" + instanceId + ", simulator=" + simulator
                + ", simulatorTask=" + simulatorTask + "]";
    }
}
