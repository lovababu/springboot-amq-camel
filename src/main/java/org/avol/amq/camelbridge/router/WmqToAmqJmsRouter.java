package org.avol.amq.camelbridge.router;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Lovababu P
 */
public class WmqToAmqJmsRouter extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AmqToWmqJmsRouter.class);

    @Value("${jbossmq.response.queue.name}")
    private String inQueue;

    @Value("${amq.out.queue}")
    private String outQueue;

    @Value("${jbossmq.mt558.messageType.value}")
    private String mt558MessageType;

    @Value("${jbossmq.mt569.messageType.value}")
    private String mt569MessageType;

    @Override
    public void configure() throws Exception {
        from(inQueue)
                .startupOrder(2)
                .log(LoggingLevel.DEBUG, log, "New message received")
                .process(exchange -> {
                    String convertedMessage = exchange.getMessage().getBody() + " is converted";
                    exchange.getMessage().setBody(convertedMessage);
                })
                .choice()
                .when(body().startsWith("{2:0569"))
                    .setHeader("MessageType", constant(mt569MessageType))
                .otherwise()
                    .setHeader("MessageType", constant(mt558MessageType))
                .to(outQueue)
                .log(LoggingLevel.DEBUG, log,
                        "Acknowledge Message is successfully sent to the output queue")
                .end();
    }
}
