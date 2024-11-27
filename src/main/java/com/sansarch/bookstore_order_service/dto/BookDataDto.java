package com.sansarch.bookstore_order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDataDto {
    private Long id;
    private String title;
    private String author;
    private Long stockAvailability;
}
