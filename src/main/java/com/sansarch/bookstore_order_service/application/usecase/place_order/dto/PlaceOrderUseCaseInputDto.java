package com.sansarch.bookstore_order_service.application.usecase.place_order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlaceOrderUseCaseInputDto {
    private List<PlaceOrderUseCaseInputBookDto> items;
}
