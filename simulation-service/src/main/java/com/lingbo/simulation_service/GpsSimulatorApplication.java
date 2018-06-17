package com.lingbo.simulation_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class GpsSimulatorApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GpsSimulatorApplication.class, args);
    }

//    @Bean
//    @ExportMetricWriter
//    public InMemoryMetricRepository inMemoryMetricRepository() {
//        return new InMemoryMetricRepository();
//    }

//    @Bean
//    public Jaxb2Marshaller getMarshaller() {
//        final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
//        jaxb2Marshaller.setClassesToBeBound(Kml.class);
//
//        final Map<String, Object> map = new HashMap<>();
//        map.put("jaxb.formatted.output", true);
//
//        jaxb2Marshaller.setMarshallerProperties(map);
//        return jaxb2Marshaller;
//    }
}