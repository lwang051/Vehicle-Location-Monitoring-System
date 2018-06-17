package com.lingbo.simulation_service.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lingbo.simulation_service.model.CurrentPosition;
import com.lingbo.simulation_service.service.PositionService;


@Service
public class DefaultPositionService implements PositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPositionService.class);

//    @Autowired
//    private KmlService kmlService;
    
    // For the use of inter-calls between services(servers)
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${com.lingbo.car.location.ingest}")
    private String carLocationIngest;

    public DefaultPositionService() {
        super();
    }

    //    @HystrixCommand(fallbackMethod = "processPositionInfoFallback")
    @Override
    public void processPositionInfo(long id, CurrentPosition currentPosition, boolean exportPositionsToKml,
                                    boolean sendPositionsToIngestionService) {

//        if (exportPositionsToKml) {
//            this.kmlService.updatePosition(id, currentPosition);
//        }

        if (sendPositionsToIngestionService) {
            this.restTemplate.postForLocation(carLocationIngest + "/api/locations", currentPosition);
        }

    }

    public void processPositionInfoFallback(long id, CurrentPosition currentPosition, boolean exportPositionsToKml,
                                            boolean sendPositionsToIngestionService) {
        LOGGER.error("Hystrix Fallback Method. Unable to send message for ingestion.");
    }

}
