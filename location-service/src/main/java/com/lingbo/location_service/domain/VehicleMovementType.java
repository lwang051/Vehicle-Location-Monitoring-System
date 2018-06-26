package com.lingbo.location_service.domain;

public enum VehicleMovementType {
	STOPPED, IN_MOTION;
	public boolean isMoving() {
		return this != STOPPED;
	}
}
