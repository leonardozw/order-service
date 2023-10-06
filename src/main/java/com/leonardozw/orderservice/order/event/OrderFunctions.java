package com.leonardozw.orderservice.order.event;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.leonardozw.orderservice.order.domain.OrderService;

import reactor.core.publisher.Flux;

@Configuration
public class OrderFunctions {
    private static final Logger log = LoggerFactory.getLogger(OrderFunctions.class);

    @Bean
    public Consumer<Flux<OrderDispatchedMessage>> dispatchOrder(OrderService orderService) {
        return flux -> orderService.consumeOrderDispatchedEvent(flux)
            .doOnNext(order -> log.info("Order {} dispatched", order.id()))
            .subscribe();
    }
}
