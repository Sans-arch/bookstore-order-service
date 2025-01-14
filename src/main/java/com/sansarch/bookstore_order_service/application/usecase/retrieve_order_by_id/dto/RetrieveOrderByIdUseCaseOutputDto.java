package com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto;

import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RetrieveOrderByIdUseCaseOutputDto {
    private Order order;
}
