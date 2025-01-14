package com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RetrieveOrderByIdUseCaseInputDto {
    private Long id;
}
