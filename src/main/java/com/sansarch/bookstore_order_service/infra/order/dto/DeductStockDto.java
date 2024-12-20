package com.sansarch.bookstore_order_service.infra.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeductStockDto {
    private Long bookId;
    private Integer quantity;
}
