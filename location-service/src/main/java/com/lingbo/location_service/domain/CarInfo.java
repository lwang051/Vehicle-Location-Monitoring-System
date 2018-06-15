package com.lingbo.location_service.domain;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Embeddable
public class CarInfo {
	
	private final String vin;
	private String engineMake;
	private String customerName;
	private String number;
	
	@SuppressWarnings("unused")
	private CarInfo() {
		this.vin = null;
	}
	
}
