package com.sansarch.bookstore_order_service.domain.order.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnavailableBookInStockException extends RuntimeException {
    private final int status = HttpStatus.CONFLICT.value();

    public UnavailableBookInStockException(Long id) {
        super("Book with id " + id + " has no available stock");
    }
}
