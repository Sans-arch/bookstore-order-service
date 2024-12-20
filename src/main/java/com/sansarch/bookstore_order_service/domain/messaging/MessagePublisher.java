package com.sansarch.bookstore_order_service.domain.messaging;


import com.sansarch.bookstore_order_service.domain.event.Event;

public interface MessagePublisher {
    void publish(Event event, String exchange);
}
