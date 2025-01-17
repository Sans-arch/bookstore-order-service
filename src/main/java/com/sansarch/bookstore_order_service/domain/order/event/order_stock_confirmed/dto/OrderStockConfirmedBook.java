package com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderStockConfirmedBook {
    private Long bookId;
}
