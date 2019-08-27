package com.pjhu.medicine.delaytask.application.service;

public class RabbitConstants {

    public static final String ORDER_QUEUE = "order-queue";
    public static final String ORDER_QUEUE_EXCHANGE = "order-queue-exchange";
    public static final String ORDER_QUEUE_ROUTING_KEY = "order-queue-routing-key";

    public static final String ORDER_DEAD_QUEUE = "order-dead-queue";
    public static final String ORDER_DEAD_QUEUE_EXCHANGE = "order-dead-queue-exchange";
    public static final String ORDER_DEAD_QUEUE_ROUTING_KEY = "order-dead-queue-routing-key";
}