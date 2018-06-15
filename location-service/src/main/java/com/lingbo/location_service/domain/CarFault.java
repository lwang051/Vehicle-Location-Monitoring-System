package com.lingbo.location_service.domain;

import javax.persistence.Embeddable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
//import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Embeddable
//@RequiredArgsConstructor
public class CarFault {
	
	private final String vin;
	private Long spn;
	private Long fmi;
	
//	@SuppressWarnings("unused")
	private CarFault() {
		this.vin = null;
	}
	
}
