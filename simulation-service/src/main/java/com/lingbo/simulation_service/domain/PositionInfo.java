package com.lingbo.simulation_service.domain;



import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO to hold position of the vehicle and some other data.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PositionInfo {
	
	private String vin;
    private Point position;
    private VehicleStatus vehicleStatus = VehicleStatus.NONE;

    /**
     * kml path is composed of a series of legs (line segments) 1 .. n.
     * this member denotes the present leg (starting at leg 0)
     */
    private Leg leg;

    /**
     * Meters from start of leg
     */
    private Double distanceFromStart;

    /**
     * The speed in m/s
     */
    private Double speed;
	
}
