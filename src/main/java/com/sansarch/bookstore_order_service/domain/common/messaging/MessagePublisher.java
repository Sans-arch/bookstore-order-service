package com.sansarch.bookstore_order_service.domain.common.messaging;


public interface MessagePublisher {
    void publish(Event event, String exchange);
    void publish(Event event, String exchange, String routingKey);
}
