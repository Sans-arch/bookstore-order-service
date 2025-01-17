package com.sansarch.bookstore_order_service.application.usecase.process_order_stock_failed.dto;

import com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed.OrderStockFailedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProcessOrderStockFailedInputDto {
    private OrderStockFailedEvent event;
}
