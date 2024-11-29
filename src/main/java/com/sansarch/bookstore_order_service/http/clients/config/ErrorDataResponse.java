package com.sansarch.bookstore_order_service.http.clients.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDataResponse {
    private int status;
    private String detail;
}
