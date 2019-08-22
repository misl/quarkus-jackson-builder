# Quarkus Reproducer: Jackson Builder

This project shows Jackson deserialization errors in native mode. These errors 
occur when making use of Jackson annotations (@JsonPOJOBuilder) supporting the 
Builder pattern.

This project consists of the following modules:

- model

    This module contains the library with a simple model. This model makes use 
    of Jackson to support (de-)serialization to JSON. Since the model itself is 
    immutable the Builder pattern is used construct new instances.
    
    Unittests are available to prove JSON (de-)serialization works properly.

- service 

    This module contains a very simple restful resource with only a post method
    to post new models to this service. A simple unittest is included to verify
    correct working.
    
    The following curl command can be used to send post request to this service:
    
    ```
    curl -X POST -H "Content-Type: application/json" \
    -d '{"version": 2, "id": "123", "value": "val"}' \
    -v localhost:8080/model
    ```
  

## Build

To build the project, run the following command from the project root directory:

```
mvn clean package
```

This build should run correctly showing no errors and no test failures.

For the remainder make the service module your current working directory:

```
cd service
```

## Package JVM

Running a JVM based version of the service can either be done with `quarkus:dev` 
or by using the JVM based runner. 

- **Using `quarkus:dev`**
    ```
    mvn quarkus:dev
    ```

- **Using JVM runner**
    ```
    java -jar target/service-1.0-SNAPSHOT-runner.jar
    ```

In either case posting new model data like described earlier should result in 
a 201 response code with the posted message in the body. For example:

```
~$ curl -X POST -H "Content-Type: application/json" -d '{"version": 2, "id": "123", "value": "val"}' -v localhost:8080/model
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /model HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 43
> 
* upload completely sent off: 43 out of 43 bytes
< HTTP/1.1 201 Created
< Connection: keep-alive
< Content-Type: application/json
< Content-Length: 38
< Date: Thu, 22 Aug 2019 13:31:48 GMT
< 
* Connection #0 to host localhost left intact
{"version":2,"id":"123","value":"val"}
```

## Package Native 

The incorrect behaviour can be trigger in various ways:

- **Integration test**
    ```
    mvn integration-test verify -Pnative
    ```

- **Native runner**
    ```
    mvn package -Pnative
    ...
    ./target/service-1.0-SNAPSHOT-runner
    ```
  
    Next posting new model data like described earlier is required to 
    trigger the error.
    
In either case the output should contain the following error:
```
2019-08-22 15:01:36,526 ERROR [io.und.req.io] (executor-thread-1) 
 Exception handling request 77f0ec3e-ffd9-448d-b427-978f93e4fb84-1 
 to /model: org.jboss.resteasy.spi.UnhandledException: 
 com.fasterxml.jackson.databind.exc.InvalidDefinitionException: 
 Builder class io.quarkus.reproducer.jacksonbuilder.model.MyModel$Builder 
 does not have build method (name: 'build')
 at [Source: (String)"{"version":3,"id":"123","value":"some"}"; line: 1, column: 1]
```     