package com.pjhu.medicine.delaytask.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitAdapter implements InitializingBean {

    private final RabbitTemplate delayRabbitTemplate;

    public String sendMessage(String exchangeName, String routingKey, Object message) {
        SpecialCorrelationData specialCorrelationData = this.specialCorrelationData(message);
        specialCorrelationData.setExchange(exchangeName);
        specialCorrelationData.setRoutingKey(routingKey);
        specialCorrelationData.setMessage(message);

        log.info("发送MQ消息，消息ID：{}，消息体:{}, exchangeName:{}, routingKey:{}",
                specialCorrelationData.getId(), message.toString(), exchangeName, routingKey);
        this.convertAndSend(exchangeName, routingKey, message, specialCorrelationData);
        return specialCorrelationData.getId();
    }

    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack && correlationData instanceof SpecialCorrelationData) {
            SpecialCorrelationData specialCorrelationData = (SpecialCorrelationData) correlationData;

            //消息发送失败,就进行重试，重试过后还不能成功就记录到数据库
            if (specialCorrelationData.getRetryCount() < 3) {
                log.info("MQ消息发送失败，消息重发，消息ID：{}，重发次数：{}，消息体:{}", specialCorrelationData.getId(),
                        specialCorrelationData.getRetryCount(), specialCorrelationData.getMessage().toString());

                // 将重试次数加一
                specialCorrelationData.setRetryCount(specialCorrelationData.getRetryCount() + 1);

                // 重发发消息
                this.convertAndSend(specialCorrelationData.getExchange(), specialCorrelationData.getRoutingKey(),
                        specialCorrelationData.getMessage(), specialCorrelationData);
            } else {
                //消息重试发送失败,将消息放到数据库等待补发
                log.warn("MQ消息重发失败，消息入库，消息ID：{}，消息体:{}", correlationData.getId(),
                        specialCorrelationData.getMessage().toString());

                // TODO 保存消息到数据库
            }
        } else {
            log.info("消息发送成功,消息ID:{}", correlationData.getId());
        }
    }

    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("MQ消息发送失败，replyCode:{}, replyText:{}，exchange:{}，routingKey:{}，消息体:{}",
                replyCode, replyText, exchange, routingKey, Arrays.toString(message.getBody()));

        // TODO 保存消息到数据库
    }

    private SpecialCorrelationData specialCorrelationData(Object message) {
        return new SpecialCorrelationData(UUID.randomUUID().toString(), message);
    }

    private void convertAndSend(String exchange, String routingKey, final Object message, SpecialCorrelationData correlationData) throws AmqpException {
        try {
            delayRabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
        } catch (Exception e) {
            log.error("MQ消息发送异常，消息ID：{}，消息体:{}, exchangeName:{}, routingKey:{}",
                    correlationData.getId(), message.toString(), exchange, routingKey, e);

            // TODO 保存消息到数据库
        }
    }

    @Override
    public void afterPropertiesSet() {
        delayRabbitTemplate.setConfirmCallback(this::confirm);
        delayRabbitTemplate.setReturnCallback(this::returnedMessage);
    }
}
