package com.sansarch.bookstore_order_service.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlaceOrderOutputDto {

    private Long orderId;
    private String status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private List<PlaceOrderOutputDtoBook> items;
}
