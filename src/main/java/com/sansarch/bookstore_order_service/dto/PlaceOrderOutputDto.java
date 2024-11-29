package com.sansarch.bookstore_order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlaceOrderOutputDto {

    private Long orderId;
    private String status;
    private BigDecimal totalPrice;
    private List<PlaceOrderOutputDtoBook> items;
}
