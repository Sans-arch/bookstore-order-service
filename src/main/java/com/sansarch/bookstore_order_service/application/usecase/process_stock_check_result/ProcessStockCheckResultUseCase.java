package com.sansarch.bookstore_order_service.application.usecase.process_stock_check_result;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_stock_check_result.dto.ProcessStockCheckResultUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_stock_check_result.dto.ProcessStockCheckResultUseCaseOutputDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProcessStockCheckResultUseCase implements UseCase<ProcessStockCheckResultUseCaseInputDto,
        ProcessStockCheckResultUseCaseOutputDto> {


    @Override
    public ProcessStockCheckResultUseCaseOutputDto execute(ProcessStockCheckResultUseCaseInputDto input) {
        return null;
    }
}
