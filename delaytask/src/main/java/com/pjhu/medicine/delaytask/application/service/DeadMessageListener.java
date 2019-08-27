package com.pjhu.medicine.delaytask.application.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeadMessageListener {

    @RabbitListener(containerFactory = "simpleRabbitListenerContainerFactory", queues = RabbitConstants.ORDER_DEAD_QUEUE)
    public void process(SendMessage sendMessage, Channel channel, Message message) throws Exception {
        log.info("[{}]处理延迟队列消息队列接收数据，消息体：{}", RabbitConstants.ORDER_DEAD_QUEUE, sendMessage.toString());
        try {
            // 确认消息已经消费成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("MQ消息处理异常，消息体:{}", e.getMessage());

            try {
                // TODO 保存消息到数据库
                // 确认消息已经消费成功
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception dbe) {
                log.error("保存异常MQ消息到数据库异常，放到死性队列，消息体：{}", dbe.getMessage());
                // 确认消息将消息放到死信队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        }
    }
}
