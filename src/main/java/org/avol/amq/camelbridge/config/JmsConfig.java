package org.avol.amq.camelbridge.config;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.avol.amq.camelbridge.router.AmqToWmqJmsRouter;
import org.avol.amq.camelbridge.router.WmqToAmqJmsRouter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * @author Lovababu P
 */
@Configuration
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${jbossmq.broker-url}")
    private String jbossMQUrl;

    @Value("${jbossmq.broker-port}")
    private int jbossMQPort;

    @Value("${jbossmq.queue.manager}")
    private String jbossQueueManager;

    @Value("${jbossmq.channel-name}")
    private String jbossChannelName;

    @Value("${jbossmq.request.queue.name}")
    private String jbossRequestQueueName;

    @Value("${jbossmq.response.queue.name}")
    private String jbossResponseQueueName;

    @Value("${jbossmq.username}")
    private String jbossUserName;

    @Bean(name = "amqConnectionFactory")
    public ConnectionFactory amqConnectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager(
            @Qualifier(value = "amqConnectionFactory") final ConnectionFactory connectionFactory) {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        return jmsTransactionManager;
    }

    @Bean
    public JmsComponent jmsComponent(@Qualifier(value = "amqConnectionFactory") final ConnectionFactory connectionFactory,
                                     final JmsTransactionManager jmsTransactionManager) {
        return JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
    }

    @Bean(name = "wmqConfig")
    public JmsConfiguration wmqJmsConfiguration(@Qualifier(value = "mqQueueConnectionFactory")
                                                            MQQueueConnectionFactory amqConnectionFactory) {
        JmsConfiguration jmsConfiguration = new JmsConfiguration();
        jmsConfiguration.setConnectionFactory(amqConnectionFactory);
        jmsConfiguration.setUsername(jbossUserName);
        jmsConfiguration.setConcurrentConsumers(5); //to configured externally.
        return jmsConfiguration;
    }

    @Bean(name = "wmq")
    public JmsComponent jmqJMSComponent(@Qualifier(value = "wmqConfig") JmsConfiguration jmsConfiguration,
                                        JmsTransactionManager jmsTransactionManager) {
        return JmsComponent.jmsComponent(jmsConfiguration);
    }

    @Bean(name = "mqQueueConnectionFactory")
    public MQQueueConnectionFactory mqQueueConnectionFactory() throws JMSException {
        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
        mqQueueConnectionFactory.setHostName(jbossMQUrl);
        mqQueueConnectionFactory.setPort(jbossMQPort);
        mqQueueConnectionFactory.setQueueManager(jbossQueueManager);
        mqQueueConnectionFactory.setChannel(jbossChannelName);
        mqQueueConnectionFactory.setTargetClientMatching(true);
        mqQueueConnectionFactory.setTransportType(1);
        return mqQueueConnectionFactory;
    }

    @Bean(value = "amq-to-wmq-bridge")
    public RouteBuilder amqToWmqRouteBuilder() {
        return new AmqToWmqJmsRouter();
    }

    @Bean(value = "wmq-to-amq-bridge")
    public RouteBuilder wmqToAmqRouteBuilder() {
        return new WmqToAmqJmsRouter();
    }
}
