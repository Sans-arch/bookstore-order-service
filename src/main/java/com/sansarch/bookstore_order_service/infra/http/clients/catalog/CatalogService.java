package com.sansarch.bookstore_order_service.infra.http.clients.catalog;

import com.sansarch.bookstore_order_service.infra.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.order.dto.DeductStockDto;
import com.sansarch.bookstore_order_service.infra.order.dto.PlaceOrderInputDtoBook;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "catalog-service",
        url = "http://localhost:8080/catalog"
)
public interface CatalogService {

    @GetMapping("/{id}")
    ResponseEntity<CatalogBookDto> getBookData(@PathVariable Long id);

    @PutMapping("/stock")
    ResponseEntity<Void> deductStock(@RequestBody List<DeductStockDto> input);

    @PostMapping("/stock/check-stock-availability")
    ResponseEntity<Void> checkStockAvailability(@RequestBody List<PlaceOrderInputDtoBook> input);
}
