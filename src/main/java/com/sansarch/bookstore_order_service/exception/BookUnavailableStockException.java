package com.sansarch.bookstore_order_service.exception;

public class BookUnavailableStockException extends RuntimeException {
    public BookUnavailableStockException(Long id) {
        super("Book with id " + id + " has no available stock");
    }
}
