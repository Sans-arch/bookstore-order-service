package com.sansarch.bookstore_order_service.application.usecase.place_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PlaceOrderUseCaseOutputBookDto {
    private Long bookId;
    private Integer quantity;
    private BigDecimal price;
}
