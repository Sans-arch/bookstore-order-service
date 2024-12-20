package com.sansarch.bookstore_order_service.domain.common.messaging;


import com.sansarch.bookstore_order_service.domain.order.event.Event;

public interface MessagePublisher {
    void publish(Event event, String exchange);
}
