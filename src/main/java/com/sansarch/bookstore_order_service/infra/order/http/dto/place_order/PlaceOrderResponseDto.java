package com.sansarch.bookstore_order_service.infra.order.http.dto.place_order;

import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlaceOrderResponseDto {
    private Long orderId;
    private OrderStatus status;
    private String message;
}
