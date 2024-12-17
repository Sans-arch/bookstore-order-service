package com.sansarch.bookstore_order_service.domain.repository;

import com.sansarch.bookstore_order_service.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
