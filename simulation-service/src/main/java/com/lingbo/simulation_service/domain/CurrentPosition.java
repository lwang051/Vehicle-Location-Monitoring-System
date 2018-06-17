package com.lingbo.simulation_service.domain;



import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CurrentPosition {
	
	private String vin;
	private Point location;
	private VehicleStatus vehicleStatus = VehicleStatus.NONE;
	private Double speed;
	private Double heading;
	private FaultCode faultCode; // TODO, seems that it has nothing to do with current position
	
}
