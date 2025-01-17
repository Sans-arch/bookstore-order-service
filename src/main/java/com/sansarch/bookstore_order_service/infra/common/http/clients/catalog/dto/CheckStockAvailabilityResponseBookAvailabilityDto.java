package com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CheckStockAvailabilityResponseBookAvailabilityDto {
    private Long bookId;
    private Boolean isAvailable;
    private Integer requestedQuantity;
    private Integer stock;
}
