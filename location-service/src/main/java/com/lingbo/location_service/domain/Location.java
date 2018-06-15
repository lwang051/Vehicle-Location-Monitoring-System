package com.lingbo.location_service.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
//import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
//@RequiredArgsConstructor
@Entity
@Table(name = "LOCATION")
public class Location {
	
	enum GpsStatus {
		EXCELLENT, OK,UNRELIABLE, BAD, NOFIX, UNKOWN;
	}
	
	public enum VehicleMovementType {
		STOPPED, IN_MOTION;
		public boolean isMoving() {
			return this != STOPPED;
		}
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "vin", 			column = @Column(name = "carInfo_vin")),
		@AttributeOverride(name = "engineMake", 	column = @Column(name = "carInfo_engineMake")),
		@AttributeOverride(name = "customerName", 	column = @Column(name = "carInfo_customerName")),
		@AttributeOverride(name = "number", 		column = @Column(name = "carInfo_number"))
	})
	private final CarInfo carInfo;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "vin",	column = @Column(name = "carFault_vin")),
		@AttributeOverride(name = "spn", 	column = @Column(name = "carFault_spn")),
		@AttributeOverride(name = "fmi", 	column = @Column(name = "carFault_fmi")),
	})
	private CarFault carFault;
	
	private double latitude;
	private double longitude;
	private String heading;
	private double gpsSpeed;
	private GpsStatus gpsStatus;
	private double odometer;
	private double totalEngineTime;
	private double totalIdleTime;
	private double totalFuelUsage;
	private String address;
	private Date timestamp = new Date();
	private String tspProvider;
	private VehicleMovementType  vehicleMovementType = VehicleMovementType.STOPPED;
	private String serviceType;
	
	// Let only me call it, but I will never call it.
//	@SuppressWarnings("unused")
	private Location() {
		this.carInfo = null;
	}
	
//	@JsonCreator
//	private Location(@JsonProperty("vin") String vin) {
//		this.carInfo = new CarInfo(vin);
//	}
	
//	public String getVin() {
//		return this.carInfo == null ?  null : this.carInfo.getVin();
//	}
	
}
