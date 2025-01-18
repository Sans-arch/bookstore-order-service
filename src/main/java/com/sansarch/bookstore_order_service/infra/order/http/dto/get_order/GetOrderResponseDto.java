package com.sansarch.bookstore_order_service.infra.order.http.dto.get_order;

import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class GetOrderResponseDto {

    private Long id;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<GetOrderResponseOrderItemDto> items;
}
