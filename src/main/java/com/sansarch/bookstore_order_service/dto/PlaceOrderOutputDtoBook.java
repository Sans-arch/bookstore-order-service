package com.sansarch.bookstore_order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PlaceOrderOutputDtoBook {
    private Long bookId;
    private Long quantity;
    private BigDecimal price;
}
