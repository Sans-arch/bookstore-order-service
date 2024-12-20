package com.sansarch.bookstore_order_service.application.repository;

import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
