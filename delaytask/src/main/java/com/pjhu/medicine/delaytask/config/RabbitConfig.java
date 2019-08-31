package com.pjhu.medicine.delaytask.config;

import com.pjhu.medicine.delaytask.application.service.RabbitConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.pjhu.medicine.delaytask.application.service.RabbitConstants.*;

@Slf4j
@EnableRabbit
@Configuration
public class RabbitConfig {

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange", RabbitConstants.ORDER_DEAD_QUEUE_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", RabbitConstants.ORDER_DEAD_QUEUE_ROUTING_KEY)
                .withArgument("x-message-ttl", 5000)
                .build();
    }

    @Bean
    public DirectExchange orderQueueExchange() {
        return new DirectExchange(RabbitConstants.ORDER_QUEUE_EXCHANGE);
    }

    @Bean
    Binding bindingOrderQueue(Queue orderQueue, DirectExchange orderQueueExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderQueueExchange)
                .with(ORDER_QUEUE_ROUTING_KEY);
    }

    @Bean
    public Queue orderDeadQueue() {
        return new Queue(ORDER_DEAD_QUEUE);
    }

    @Bean
    public FanoutExchange orderDeadQueueExchange() {
        return new FanoutExchange(ORDER_DEAD_QUEUE_EXCHANGE);
    }

    @Bean
    Binding bindingOrderDeadQueue(Queue orderDeadQueue, FanoutExchange orderDeadQueueExchange) {
        return BindingBuilder
                .bind(orderDeadQueue)
                .to(orderDeadQueueExchange);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(5);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public RabbitTemplate delayRabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }
}
