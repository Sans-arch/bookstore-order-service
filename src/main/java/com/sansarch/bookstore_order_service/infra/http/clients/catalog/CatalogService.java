package com.sansarch.bookstore_order_service.infra.http.clients.catalog;

import com.sansarch.bookstore_order_service.infra.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.dto.DeductStockDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
