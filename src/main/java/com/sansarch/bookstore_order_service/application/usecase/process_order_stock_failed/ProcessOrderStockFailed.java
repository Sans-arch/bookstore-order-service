package com.sansarch.bookstore_order_service.application.usecase.process_order_stock_failed;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_failed.dto.ProcessOrderStockFailedInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_failed.dto.ProcessOrderStockFailedOutputDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProcessOrderStockFailed implements UseCase<ProcessOrderStockFailedInputDto, ProcessOrderStockFailedOutputDto> {

    @Override
    public ProcessOrderStockFailedOutputDto execute(ProcessOrderStockFailedInputDto input) {
        return null;
    }
}
