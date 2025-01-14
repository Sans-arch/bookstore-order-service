package com.sansarch.bookstore_order_service.infra.common.http.clients.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDataResponse {
    private int status;
    private String detail;
}
