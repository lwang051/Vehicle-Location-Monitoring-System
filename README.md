# Vehicle-Location-Monitoring-System
This is a system that monitors vehicles' locations on google maps. In real life vehicles are running on roads and real-time data such as the latitude and the longitude are gathered from the moving vehicles, while in this project a simulation service is introduced as a substitution of the otherwise much heavier work. Vehicle initial locations can be posted and stored in database. The system simulates the moving vehicles and visualize the movement on google maps on the dashboard. You will see the vehicles moving along the [**pre-defined paths**](../master/simulation-service/src/main/resources/paths.json). Note that each path is defined as a polyline encrypted to a string. You can customize your google maps polylines [**here**](https://developers.google.com/maps/documentation/utilities/polylineutility).
### Demo
Check out the Youtube video here :)

[![IMAGE ALT TEXT HERE](../master/docs/youtube.png)](https://youtu.be/QBf3dIw6LmQ)

Here is a quick view of the video.

![alt text](../master/docs/trim1.gif)

![alt text](../master/docs/trim2.gif)

![alt text](../master/docs/trim3.gif)
### Architecture Diagram
The business logic is simple, yet the whole system is built in microservice architecture. Services are well partitioned and isolated, communicating to each other through RESTful APIs. Application health monitoring, service registry/discovery, and circuit breaker are integrated into the system. This project is done using Java and Spring tech stack.

![alt text](../master/docs/Architecture%20Diagram.png)

### Activity Diagram

![alt text](../master/docs/Activity%20Diagram.png)
### Environment
1. 14G+ RAM (If runs on one node)
2. Web Browser
3. Terminal/Command Line Tool
4. JDK 1.8
5. Maven
6. Docker
7. Postman
### Commands
Follow the instructions below to try out this project and see the outcome :)
#### Pre-steps:
1. Run Docker.
2. Run Postman.
3. In Terminal/Command Line Tool, change the directory (cd) into the project directory (Vehicle-Location-Monitoring-System).
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
Change the directory to "sh". Type EACH command below in a NEW Terminal tab.

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
    
Change HTTP verb to "POST". Under the url, change the tab to "Body". Copy the [**initial vehicle locations**](../master/location-service/location.json) (Json format), and paste it into the "Body" input box. Press "Send". If successful, the HTTP response status "201 Created" will be seen.
#### In Web Browser:
Open a web browser window to see the dashboard with the vehicles located at their initial locations:

    http://localhost:8080
    
Open another web browser window to see the simulator control panel:

    http://localhost:9005
    
Click "Start simulation".
#### Shut down:
1. Close web browser windows.
2. In each Terminal tab, press Ctrl + C to shut down each service.
3. Shut down Docker.
4. Shut down Postman.
