package com.sansarch.bookstore_order_service.clients;

import com.sansarch.bookstore_order_service.dto.BookDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "catalog-service",
        url = "http://localhost:8080/catalog"
)
public interface CatalogServiceClient {

    @GetMapping("/{id}")
    ResponseEntity<BookDataDto> getBookData(@PathVariable Long id);
}
