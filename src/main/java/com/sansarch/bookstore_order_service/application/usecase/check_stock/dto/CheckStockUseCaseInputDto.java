package com.sansarch.bookstore_order_service.application.usecase.check_stock.dto;

import com.sansarch.bookstore_order_service.domain.order.event.StockCheckEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CheckStockUseCaseInputDto {
    private StockCheckEvent event;
}
