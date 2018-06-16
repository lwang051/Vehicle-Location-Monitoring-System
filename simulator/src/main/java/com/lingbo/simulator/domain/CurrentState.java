package com.lingbo.simulator.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CurrentState {
	
	private String vin;
	private Point point;
	private ServiceType serviceType = ServiceType.NONE;
	private Double speed;
	private Double heading;
	private Leg Leg;
	private Double metersFromStartLeg;
	private FaultCode faultCode; // TODO, seems that it has nothing to do with current position
	
}
