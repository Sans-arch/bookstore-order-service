package com.sansarch.bookstore_order_service.domain.messaging;


import com.sansarch.bookstore_order_service.domain.entity.Order;

public interface MessagePublisher {
    void publish(Order order);
}
