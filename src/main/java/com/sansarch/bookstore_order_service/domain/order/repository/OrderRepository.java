package com.sansarch.bookstore_order_service.domain.order.repository;

import com.sansarch.bookstore_order_service.domain.order.entity.Order;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findById(Long id);
}
