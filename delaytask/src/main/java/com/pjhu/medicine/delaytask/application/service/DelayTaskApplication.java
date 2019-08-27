package com.pjhu.medicine.delaytask.application.service;

import com.pjhu.medicine.delaytask.application.adapter.persisitence.RabbitAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.pjhu.medicine.delaytask.application.service.RabbitConstants.ORDER_QUEUE_EXCHANGE;
import static com.pjhu.medicine.delaytask.application.service.RabbitConstants.ORDER_QUEUE_ROUTING_KEY;

@Service
@RequiredArgsConstructor
public class DelayTaskApplication {

    private final RabbitAdapter rabbitAdapter;

    @Transactional
    public String create(SendMessage sendMessage) {
        return rabbitAdapter.sendMessage(ORDER_QUEUE_EXCHANGE, ORDER_QUEUE_ROUTING_KEY, sendMessage);
    }
}
