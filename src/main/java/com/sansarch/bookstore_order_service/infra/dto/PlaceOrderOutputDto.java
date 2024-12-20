package com.sansarch.bookstore_order_service.infra.dto;

import com.sansarch.bookstore_order_service.domain.entity.OrderStatus;
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
