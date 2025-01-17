package com.sansarch.bookstore_order_service.infra.order.http.dto.place_order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceOrderRequestBookDto {
    private Long bookId;
    private Long quantity;
}
