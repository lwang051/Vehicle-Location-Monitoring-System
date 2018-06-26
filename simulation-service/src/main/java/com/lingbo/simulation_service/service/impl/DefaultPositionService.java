package com.lingbo.simulation_service.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lingbo.simulation_service.model.CurrentPosition;
import com.lingbo.simulation_service.service.PositionService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class DefaultPositionService implements PositionService {

//    @Autowired
//    private KmlService kmlService;
    
    // For the use of inter-calls between services(servers)
//    private RestTemplate restTemplate = new RestTemplate();
    @Autowired // created by spring-cloud-eureka
    private RestTemplate restTemplate;

    @Value("${next.service.url}")
    private String nextServiceUrl;

    public DefaultPositionService() {
        super();
    }

    @HystrixCommand(fallbackMethod = "processPositionInfoFallback")
    @Override
    public void processPositionInfo(long id, 
    		CurrentPosition currentPosition, 
    		boolean exportPositionsToKml, 
    		boolean sendPositionsToIngestionService) {

//        if (exportPositionsToKml) {
//            this.kmlService.updatePosition(id, currentPosition);
//        }

        if (sendPositionsToIngestionService) {
            this.restTemplate.postForLocation(nextServiceUrl, currentPosition);
        }

    }

    public void processPositionInfoFallback(long id, 
    		CurrentPosition currentPosition, 
    		boolean exportPositionsToKml, 
    		boolean sendPositionsToIngestionService) {
    	log.error("Hystrix Fallback Method. Unable to send messages to message-sink-service.");
    }

}
