package com.sansarch.bookstore_order_service.application.usecase.process_order.dto;

import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProcessOrderUseCaseInputDto {
    private OrderCreatedEvent event;
}
