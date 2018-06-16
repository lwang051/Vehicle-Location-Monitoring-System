package com.lingbo.simulator.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonPropertyOrder({"requestSize", "simulatorRequests"})
public class SimulatorFixture {
	
	private List<SimulatorRequest> simulatorRequests = new ArrayList<SimulatorRequest>();
	
	public int getRequestSize() {
        return simulatorRequests.size();
    }
	
	public void setSimulatorRequests(List<SimulatorRequest> simulatorRequests) {
        Assert.notEmpty(simulatorRequests, "simulatorRequests must not be empty.");
        this.simulatorRequests = simulatorRequests;
    }
	
	public boolean usesKmlIntegration() {
        return simulatorRequests.stream().anyMatch(request -> request.isExportPositionsToKml());
    }
}
