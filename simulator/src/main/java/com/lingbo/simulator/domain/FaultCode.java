package com.lingbo.simulator.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FaultCode {
	
	private String engineMake;
    private String faultCode;
    private String faultCodeId;
    private String faultCodeClassification;
    private String description;
    private String repairInstructions;
    private String fmi;
    private String sa;
    private String spn;
    
}
