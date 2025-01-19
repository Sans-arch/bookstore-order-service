package com.sansarch.bookstore_order_service.infra.common.http.clients.catalog;

import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputBookDto;
import com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.dto.CheckStockAvailabilityResponseDto;
import com.sansarch.bookstore_order_service.infra.order.dto.DeductStockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bookstore-catalog-service")
public interface CatalogServiceClient {

    @GetMapping("/catalog/{id}")
    ResponseEntity<CatalogBookDto> getBookData(@PathVariable Long id);

    @PutMapping("/catalog/stock")
    ResponseEntity<Void> deductStock(@RequestBody List<DeductStockDto> input);

    @PostMapping("/catalog/stock/check-stock-availability")
    ResponseEntity<CheckStockAvailabilityResponseDto> checkStockAvailability(@RequestBody List<PlaceOrderUseCaseInputBookDto> input);
}
