package com.sansarch.bookstore_order_service.infra.order.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class GetOrderResponseOrderItemDto {

    private Long id;
    private Long bookId;
    private Integer quantity;
    private BigDecimal price;
}
