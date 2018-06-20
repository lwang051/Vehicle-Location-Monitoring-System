package message_sink_service;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Fleet Location Updater application.
 */
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker
public class FleetLocationUpdaterApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FleetLocationUpdaterApplication.class, args);
    }

}
