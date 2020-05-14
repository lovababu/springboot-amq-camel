package org.avol.amq.camelbridge.router;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.avol.amq.camelbridge.config.JmsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@RunWith(CamelSpringBootRunner.class)
@ContextConfiguration(classes = JmsConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AmqToWmqJmsRouterTest extends CamelTestSupport {

    @Autowired
    private AmqToWmqJmsRouter amqToWmqJmsRouter;

    @Autowired
    private WmqToAmqJmsRouter wmqToAmqJmsRouter;

    @Test
    public void testAmqToJbossMQRouter() {
        assertNotNull(amqToWmqJmsRouter);
    }

    @Test
    public void testWmqToAmqJmsRouter() {
        assertNotNull(wmqToAmqJmsRouter);
    }
}
