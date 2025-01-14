package com.sansarch.bookstore_order_service.infra.order.repository;

import com.sansarch.bookstore_order_service.infra.order.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderRepository extends JpaRepository<OrderModel, Long> {
}
