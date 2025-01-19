package com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderStockFailedBook {
    private Long bookId;
}
