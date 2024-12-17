package com.sansarch.bookstore_order_service.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlaceOrderInputDto {
    private List<PlaceOrderInputDtoBook> items;
}
