package com.sansarch.bookstore_order_service.application.usecase.check_stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckStockUseCaseOutputDto {
    private boolean areAllBooksAvailable;
}
