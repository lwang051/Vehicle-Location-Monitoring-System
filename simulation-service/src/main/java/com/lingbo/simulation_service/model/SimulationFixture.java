package com.lingbo.simulation_service.model;


//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SimulationFixture {

    private List<SimulationThreadRequest> simulationThreadRequests = new ArrayList<>(0);

    public int getNumberOfSimulationThreadRequests() {
        return simulationThreadRequests.size();
    }

    public void setSimulationThreadRequests(List<SimulationThreadRequest> simulationThreadRequests) {
        Assert.notEmpty(simulationThreadRequests, "simulationThreadRequests must not be empty.");
        this.simulationThreadRequests = simulationThreadRequests;
    }

}
