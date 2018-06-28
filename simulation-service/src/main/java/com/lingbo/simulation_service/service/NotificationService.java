package com.lingbo.simulation_service.service;

import com.lingbo.simulation_service.model.CurrentPosition;

public interface NotificationService {

    void send(long id, 
    	CurrentPosition currentPosition, 
    	boolean exportPositionsToKml, 
    	boolean exportPositionsToMessaging);

}