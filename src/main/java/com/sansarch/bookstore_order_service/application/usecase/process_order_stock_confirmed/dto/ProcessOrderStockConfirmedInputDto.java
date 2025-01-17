package com.sansarch.bookstore_order_service.application.usecase.process_order_stock_confirmed.dto;

import com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed.OrderStockConfirmedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProcessOrderStockConfirmedInputDto {
    private OrderStockConfirmedEvent event;
}
