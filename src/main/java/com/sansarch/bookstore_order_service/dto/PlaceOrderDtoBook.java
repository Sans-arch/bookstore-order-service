package com.sansarch.bookstore_order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceOrderDtoBook {
    private Long bookId;
    private Long quantity;
}
