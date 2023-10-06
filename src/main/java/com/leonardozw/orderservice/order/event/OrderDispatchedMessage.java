package com.leonardozw.orderservice.order.event;

public record OrderDispatchedMessage(
    Long orderId
) {}
