package com.lingbo.location_service.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Embeddable
@RequiredArgsConstructor
public class FaultCode {
	
	private String engineMake;
	private String faultCode;
	private String faultCodeId;
	private String faultCodeClassification;
	private String description;
	@Column(length = 1024)
	private String repairInstructions;
	private Long fmi;
	private String sa;
	private Long spn;
	
}
