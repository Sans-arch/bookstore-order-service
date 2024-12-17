package com.sansarch.bookstore_order_service.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookNotFoundException extends RuntimeException {
    private final int status = HttpStatus.NOT_FOUND.value();

    public BookNotFoundException(Long id) {
        super("Book with id " + id + " not found");
    }
}
