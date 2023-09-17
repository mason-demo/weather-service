# Weather Service API
This is Weather Service Demo to get weather information through geographic information.

The following sections introduces how to Prepare, Run, Build, Deploy and Test the Weather Service

# Introduction
## Design:

Modular Structure: The project is systematically divided into weather-service-core & weather-service-api modules, ensuring clarity and easier maintainability.

Code Structure: The project follow Spring Boot recommend layer to manage codes.
Clear layers with controller, service, model, repository and well utilization of Spring Boot features like annotation, config, exception handling, interceptor, etc.

Configuration: Uses the application.yml, application-test.yml and application-test.yml for development and deployment sandbox.

## Technologies:

Java (JDK 17): Programing Language to ensure consistent and reliable performance across different platforms.

Maven: Manages project dependencies & builds. 

Docker: Uses docker to deploy Redis and Weather Service

Spring Boot: The main framework that streamlines the process of Weather Service.

H2 DB: A lightweight in-memory database to Store Weather Data which is very suitable for this Demo project.

JPA: The Java Persistence API to interact with H2 DB for weather Data with ease.

Redis: Caches apikey, apikey access limit for fast response.

## User Interaction & Testing:
 
Swagger UI: Offers an interactive way for user to undersand and test API.

Maven Testing: Uses Unit Test to do positive and negative tests.

Postman Test: Uses Postman to do API debug, integration test and automation test.

# Development & Depoyment
## Software Requirement

IntelliJ IDEA
Version: Latest stable version recommended.

Java
    Version: JDK 17 (Java Development Kit)

Maven
    Version: 3.2.0. Please use ./mvnw to build to get related maven version automatically.

Docker
    Version: Latest stable version recommended.


## Run in Development mode
Step 1: Get latest IntelliJ and open the root path of this project.

Step 2: Configure openweather

 - Open weather-service-api/src/main/resources/application.yml
 - Configure openweather api-key

Step 3: Start Redis Server

 - Go to ./docker

    `cd ./docker`

 - Start Redis Server

    `docker compose up weather-service-redis -d`

Step 4: Run project in IDE

 - Open project using IntelliJ
 - run *com.mason.weather.WeatherServiceApiApplication* under *weather-service-api* module

Step 5: Verify API

- Go to [API Test](#api-test) section

## Build project for Docker
The script does the following tasks

 - Run Maven Build 
 - Copy Jar file to docker directory and artifacts
 - Zip docker related files and copy to artifacts directory

Build Command

    sh ./script/build.sh

If it fails to execute `./mvnw build` or `./mvnw test`, please try the command in project root path

    mvn wrapper:wrapper

## Run Project in Docker
After the above build, we can try run program in docker.
Note: please stop application run by main class in ItelliJ to avoid port conflict.

Go to docker directory

    cd ./docker

Run docker compose which build/run both Redis and Weather Service

    docker compose up -d --build

Start/Stop/Restart docker service

    sh ./script/run.sh --start redis

    sh ./script/run.sh --stop redis

    sh ./script/run.sh --restart redis

    sh ./script/run.sh --start api

    sh ./script/run.sh --stop api

    sh ./script/run.sh --restart api

Verify API

- Go to [API Test](#api-test) section

## API Test
### Quick Test in Brower

- Access the address in Browser http://localhost:8080/api/weather?apikey=api_key_1&city=London&country=England
- It is expected to return results like 

```
{
  "status": "success",
  "code": 200,
  "data": {
    "description": "haze",
    "createdTime": "2023-09-16 15:25:22",
    "updatedTime": "2023-09-16 15:25:22"
  }
}
```

- Keep refresh Browser for 5 times
- It is expected to show apikey rate limit exceed error.

```
{
    "status": "error",
    "code": 429,
    "message": "API Key Rate Limit Exceeded"
}
```

- Need to wait for 1 hour or clean redis cache immediately to reset API Key access limit

Clean Redis Cache command

    docker exec weather-service-redis redis-cli FLUSHALL

### Test in Swagger UI

- Access API Doc
http://localhost:8080/v3/api-docs

It is expected to show openapi JSON file 

- Access Swagger UI
http://localhost:8080/swagger-ui/index.html

Click on *Try it out* and *Execute* seperately with default value

It is expected to return success result like this

```
{
  "status": "success",
  "code": 200,
  "data": {
    "description": "haze",
    "createdTime": "2023-09-16 15:25:22",
    "updatedTime": "2023-09-16 15:25:22"
  }
}
```

### Maven Test
Java Unit Test has been written to test Backend Code.
The test covers Controller, Service, Repository Layer

Run Unit Test in project Root directory

    ./mvnw test

Test results will display like

```
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.746 s - in com.mason.weather.WeatherServiceApplicationTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0

```

### Postman API Test in Batch
The Restful API can be tested by Postman for integration test/automation test

Postman collection has been exported under *./postman/weather-service.postman_collection.json*

Steps to use it
1. Import the collection to Postman Client
2. Click on weather-service
3. Click on the Run button on the right-top corner
4. Click on *Run weather-service* button(The params Iterations and Delay can be adjusted)

Postman will run the tests one by one

Adjust Test Case: The postman testcase is written under Request/Tests tab like bellow
```
pm.test("HTTP Status is 400", () => {
    pm.response.to.have.status(400);
});

const result = pm.response.json();

pm.test("Response status is error", () => {
    pm.expect(result.status).to.equal("error");
});

pm.test("Response code is 400", () => {
    pm.expect(result.code).to.equal(400);
});

pm.test("Error message is 'At least one of 'city' or 'country' must have a value'", () => {
    pm.expect(result.message).to.equal("At least one of 'city' or 'country' must have a value");
});

```
It can be adjusted if Weather Service has any adjustment.

# Other Information for Debug/Monitor
## Redis Commands

Clean Redis Cache

    docker exec weather-service-redis redis-cli FLUSHALL

Connect to Redis docker

    docker exec -it weather-service-redis redis-cli

List all keys
    
    KEYS *

Get value of a key

    GET [key-name]

Delete a key

    DEL [key-name]

Flush All

    FLUSHALL

Get key timeout

    TTL [key-name]

