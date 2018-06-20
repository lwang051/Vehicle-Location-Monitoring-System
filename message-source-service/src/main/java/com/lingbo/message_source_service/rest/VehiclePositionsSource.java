package com.lingbo.message_source_service.rest;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@EnableBinding(Source.class)
@RestController
public class VehiclePositionsSource {

    @Autowired
    private MessageChannel output;

    @RequestMapping(path = "/api/locations", method = RequestMethod.POST)
    public void locations(@RequestBody String positionInfo) {
        this.output.send(MessageBuilder.withPayload(positionInfo).build());
    }

}
