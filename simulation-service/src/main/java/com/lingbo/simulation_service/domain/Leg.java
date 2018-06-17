package com.lingbo.simulation_service.domain;



import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Leg {
	
	private Integer id;
    private Point start;
    private Point end;
    private Double length;
    private Double heading;
	
}
