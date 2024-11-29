package com.sansarch.bookstore_order_service.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CatalogBookDto {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Long stockAvailability;
}
