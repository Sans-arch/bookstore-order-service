package com.sansarch.bookstore_order_service.application.usecase.process_order_stock_confirmed;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_confirmed.dto.ProcessOrderStockConfirmedInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_confirmed.dto.ProcessOrderStockConfirmedOutputDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProcessOrderStockConfirmed implements UseCase<ProcessOrderStockConfirmedInputDto, ProcessOrderStockConfirmedOutputDto> {

    @Override
    public ProcessOrderStockConfirmedOutputDto execute(ProcessOrderStockConfirmedInputDto input) {
        return null;
    }
}
