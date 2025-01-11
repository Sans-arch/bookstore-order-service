package com.sansarch.bookstore_order_service.application.usecase.place_order.dto;

import com.sansarch.bookstore_order_service.infra.order.dto.PlaceOrderInputDtoBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderUseCaseInputDto {
    private List<PlaceOrderInputDtoBook> items;
}
