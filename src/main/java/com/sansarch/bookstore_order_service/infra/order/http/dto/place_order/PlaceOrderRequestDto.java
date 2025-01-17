package com.sansarch.bookstore_order_service.infra.order.http.dto.place_order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequestDto {
    private List<PlaceOrderRequestBookDto> items;
}
