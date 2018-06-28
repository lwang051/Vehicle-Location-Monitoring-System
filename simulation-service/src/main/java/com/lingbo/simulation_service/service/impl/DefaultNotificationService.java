package com.lingbo.simulation_service.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lingbo.simulation_service.model.CurrentPosition;
import com.lingbo.simulation_service.service.NotificationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class DefaultNotificationService implements NotificationService {

//    @Autowired
//    private KmlService kmlService;
    
    // For the use of inter-calls between services(servers)
//    private RestTemplate restTemplate = new RestTemplate();
    @Autowired // created by spring-cloud-eureka
    private RestTemplate restTemplate;

    @Value("${next.service.url}")
    private String nextServiceUrl;

    public DefaultNotificationService() {
        super();
    }

    @HystrixCommand(fallbackMethod = "pushFallback")
    @Override
    public void send(long id, 
    		CurrentPosition currentPosition, 
    		boolean exportPositionsToKml, 
    		boolean exportPositionsToMessaging) {
        if (exportPositionsToMessaging) {
            this.restTemplate.postForLocation(nextServiceUrl, currentPosition);
        }
    }

    public void pushFallback(long id, 
    		CurrentPosition currentPosition, 
    		boolean exportPositionsToKml, 
    		boolean exportPositionsToMessaging) {
    	log.error("Hystrix Fallback Method. Unable to send messages to message-sink-service.");
    }

}
