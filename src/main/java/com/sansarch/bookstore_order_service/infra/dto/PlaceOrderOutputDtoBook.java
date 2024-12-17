package com.sansarch.bookstore_order_service.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PlaceOrderOutputDtoBook {
    private Long bookId;
    private Integer quantity;
    private BigDecimal price;
}
