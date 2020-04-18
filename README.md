# Apache ActiveMQ and Camel example.
 
  Sample application to receive messages from ActiveMQ through Apache Camel Integration framework, 
  and enrich message send to output broker of ActiveMQ. 

## TechStack.
 1. Apache ActiveMQ
 2. Apache Camel Integration framework.
 3. Springboot framework.
 
### Setup ActiveMQ.

    docker pull rmohr/activemq
    docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
    
### Build the application.

    $mvn clean install
    
    
### Run the application.

    Run CamelbridgeApplication.java as main application.
    
    OR
    
    $./mvnw spring-boot:run