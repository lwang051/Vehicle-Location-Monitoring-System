package com.lingbo.simulation_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableCircuitBreaker
public class GpsSimulatorApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GpsSimulatorApplication.class, args);
    }
    
}