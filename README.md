# Vehicle-Location-Monitoring-System
This is a system that monitors vehicles' locations on google maps. In real life vehicles are running on roads and real-time data such as the latitude and the longitude are gathered from the moving vehicles, while in this project a simulation service is introduced as a substitution of the otherwise much heavier work. Vehicle initial locations can be posted and stored in database. The system then simulates the moving vehicles and visualize the movement on google maps on the dashboard. See the activity diagram below.

![alt text](../master/docs/Activity%20Diagram.png)

The business logic is simple, yet the whole system is built in microservice architecture. Services are well partitioned and isolated, communicating to each other through RESTful APIs. Application health monitoring, service registry/discovery, and circuit breaker are integrated into the system. This project is done using Java and Spring tech stack. See the architecture diagram below.

![alt text](../master/docs/Architecture%20Diagram.png)
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
Change directory to "sh". Type in each command below in a new Terminal tab.

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
    
Click "Start simulation". Change to the dashboard window. See the vehicles moving along the [**pre-defined paths**](../master/simulation-service/src/main/resources/paths.json). Note that each path is defined as a polyline encrypted to a string.
#### Shut down:
1. Close web browser windows.
2. In each Terminal tab, press Ctrl + C to shut down each service.
3. Shut down Docker.
4. Shut down Postman.
