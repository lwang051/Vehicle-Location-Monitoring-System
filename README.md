# Vehicle-Location-Monitoring-System
This is a system that monitors vehicles' locations on google maps. In real life vehicles are running on roads and real-time data such as the latitude and the longitude are gathered from the moving vehicles themselves, while in this project a simulation service is introduced as a substitution of the otherwise much heavier work.

The system simulates the moving/non-moving vehicles, and finally visualize the movement on google maps to the client. The business logic is simple, yet the whole system is built in microservice architecture. Services are well partitioned and isolated, communicating to each other through RESTful APIs. Application health monitoring, service registry/discovery, and circuit breaker are integrated into the system, and more needs to be considered to meet the industry standard and make it a full fledged application. This project is done using Java and Spring tech stack.
### Environment
1. 14G+ RAM (assuming running on one node)
2. Web Browser
3. Terminal/Command Line Tool
4. JRE 1.8
5. Maven
6. Docker
7. Postman
### Command
Run Docker. Run Postman. In Terminal/Command Line Tool, change the directory (cd) into the project directory (Vehicle-Location-Monitoring-System).
#### Build:

    cd monitoring-system
    mvn clean install
    cd ..
    cd operation-system
    mvn clean install
    cd ..

#### Launch RabbitMQ:

    docker-compose up
    
#### Start each service:

    cd sh
    sh eureka-service-start.sh
    sh hystrix-service-start.sh
    sh location-service-start.sh
    sh simulation-service-start.sh
    sh message-source-service-start.sh
    sh message-sink-service-start.sh
    sh dashboard-start.sh
    
#### In Postman:
Type this url:

    http://localhost:9000/init
    
Change HTTP verb to "POST". Under the url, change the tab to "Body". Copy the [**initial vehicle locations**](../master/location-service/location.json) (Json format), and paste it into the "Body" input box. Press "Send". If successful, a HTTP response code "Created 201" will be seen.
#### In Web Browser:
Open the dashboard to see the initial locations of the vehicles:

    http://localhost:8080
    
Open another web browser window, and open the simulator:

    http://localhost:9005
    
Press "Start simulation". Change to the dashboard window. See the vehicles moving along the [**pre-defined paths**](../master/simulation-service/src/main/resources/fixture.json). Note that each path is defined as a polyline encrypted to a string.
