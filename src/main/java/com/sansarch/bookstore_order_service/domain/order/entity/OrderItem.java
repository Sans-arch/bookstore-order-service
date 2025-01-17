package com.sansarch.bookstore_order_service.domain.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
public class OrderItem {

    private Long id;
    private Long bookId;
    private Long quantity;
    private BigDecimal price;
}
