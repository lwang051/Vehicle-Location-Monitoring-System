package com.lingbo.message_source_service;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MessageSourceServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MessageSourceServiceApplication.class);
    }

}
