package com.sansarch.bookstore_order_service.application.usecase.place_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderUseCaseInputBookDto {
    private Long bookId;
    private Integer quantity;
}
