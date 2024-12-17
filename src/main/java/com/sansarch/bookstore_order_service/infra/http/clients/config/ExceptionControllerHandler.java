package com.sansarch.bookstore_order_service.infra.http.clients.config;

import com.sansarch.bookstore_order_service.domain.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.domain.exception.OrderNotFoundException;
import com.sansarch.bookstore_order_service.domain.exception.UnavailableBookInStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDataResponse> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        var errorData = new ErrorDataResponse(ex.getStatus(), ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus())).body(errorData);
    }

    @ExceptionHandler(UnavailableBookInStockException.class)
    public ResponseEntity<ErrorDataResponse> handleUnavailableBookInStockException(UnavailableBookInStockException ex, WebRequest request) {
        var errorData = new ErrorDataResponse(ex.getStatus(), ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus())).body(errorData);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDataResponse> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
        var errorData = new ErrorDataResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorData);
    }
}