package com.sansarch.bookstore_order_service.domain.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order with id " + id + " not found");
    }
}
