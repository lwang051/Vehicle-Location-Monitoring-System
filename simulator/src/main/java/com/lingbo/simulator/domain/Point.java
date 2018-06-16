package com.lingbo.simulator.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Point {
	
	private Double latitude;
	private Double longitude;
	
}
