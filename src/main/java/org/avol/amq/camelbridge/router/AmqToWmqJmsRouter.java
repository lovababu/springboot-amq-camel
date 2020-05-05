package org.avol.amq.camelbridge.router;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Lovababu P
 */
public class AmqToWmqJmsRouter extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(AmqToWmqJmsRouter.class);

    @Value("${amq.in.queue}")
    private String inQueue;

    @Value("${jbossmq.request.queue.name}")
    private String outQueue;

    @Value("${amq.messagetype.value}")
    private String messageType;


    @Override
    public void configure() throws Exception {
        from(inQueue)
                .startupOrder(1)
                .log(LoggingLevel.DEBUG, log, "New message received")
                .process(exchange -> {
                    String convertedMessage = exchange.getMessage().getBody() + " is converted";
                    exchange.getMessage().setBody(convertedMessage);
                })
                .setHeader("MessageType", constant(messageType))
                .to(outQueue)
                .log(LoggingLevel.DEBUG, log, "Message is successfully sent to the output queue")
                .end();
    }
}
