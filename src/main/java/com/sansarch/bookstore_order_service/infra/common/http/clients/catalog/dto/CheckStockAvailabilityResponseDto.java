package com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CheckStockAvailabilityResponseDto {
    private Boolean allAvailable;
    private List<CheckStockAvailabilityResponseBookAvailabilityDto> booksAvailability;
}
