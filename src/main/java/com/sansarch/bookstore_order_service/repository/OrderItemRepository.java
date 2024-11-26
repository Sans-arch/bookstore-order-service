package com.sansarch.bookstore_order_service.repository;

import com.sansarch.bookstore_order_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
