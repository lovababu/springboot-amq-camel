# Apache ActiveMQ and Camel example.
 
  Sample application to receive messages from ActiveMQ through Apache Camel Integration framework, 
  and enrich message send to output broker of ActiveMQ. 

## TechStack.
 1. Apache ActiveMQ
 2. Apache Camel Integration framework.
 3. Springboot framework.
 
### Setup ActiveMQ.

    docker pull rmohr/activemq
   
Then run interactive:

    docker run -it -p 61616:61616 -p 8161:8161 rmohr/activemq
    
Or in the background:

    docker run -d -p 61616:61616 -p 8161:8161 rmohr/activemq
    
### Build the application

Requires JDK8.  JDK13 will not work with this example.

    mvn clean install
    
    
### Run the application.

Run `CamelbridgeApplication.java` as main application.
    
OR
    
    mvnw spring-boot:run
    
Then, open the ActiveMQ admin on port 8161 and view the queue.

