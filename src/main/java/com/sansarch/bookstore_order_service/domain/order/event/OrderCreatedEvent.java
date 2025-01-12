package com.sansarch.bookstore_order_service.domain.order.event;

import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputBookDto;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderCreatedEvent extends Event {
    private final Long orderId;
    private final OrderStatus status;
    private final List<PlaceOrderUseCaseInputBookDto> items;

    public OrderCreatedEvent(Long orderId, OrderStatus status, List<PlaceOrderUseCaseInputBookDto> items) {
        super(orderId);
        this.orderId = orderId;
        this.status = status;
        this.items = items;
    }
}
