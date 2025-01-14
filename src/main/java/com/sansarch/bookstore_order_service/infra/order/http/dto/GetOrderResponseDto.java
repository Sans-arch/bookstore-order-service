package com.sansarch.bookstore_order_service.infra.order.http.dto;

import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class GetOrderResponseDto {

    private Long id;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String createdAt;
    private List<GetOrderResponseOrderItemDto> items;
}
