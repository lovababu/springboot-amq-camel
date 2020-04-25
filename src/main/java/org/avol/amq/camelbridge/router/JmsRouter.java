package org.avol.amq.camelbridge.router;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Lovababu P
 */
public class JmsRouter extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(JmsRouter.class);

    @Value("${input.queue}")
    private String inQueue;

    @Value("${output.queue}")
    private String outQueue;

    @Override
    public void configure() throws Exception {
        from(inQueue)
                .startupOrder(1)
                .log(LoggingLevel.DEBUG, log, "New message received")
                .process(exchange -> {
                    String convertedMessage = exchange.getMessage().getBody() + " is converted";
                    System.out.println(">>>>>>>>>>>>>>>>>>>> " + convertedMessage);
                    exchange.getMessage().setBody(convertedMessage);
                })
                .to("wmq:queue:COLLINE.SWIFT.D1")
                .log(LoggingLevel.DEBUG, log, "Message is successfully sent to the output queue")
                .end();
    }
}
