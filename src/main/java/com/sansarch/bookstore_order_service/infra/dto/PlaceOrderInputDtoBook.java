package com.sansarch.bookstore_order_service.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceOrderInputDtoBook {
    private Long bookId;
    private Integer quantity;
}
