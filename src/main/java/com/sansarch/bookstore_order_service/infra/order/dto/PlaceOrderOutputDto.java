package com.sansarch.bookstore_order_service.infra.order.dto;

import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlaceOrderOutputDto {
    private Long orderId;
    private OrderStatus status;
    private String message;
}
