package com.sansarch.bookstore_order_service.domain.event;

import com.sansarch.bookstore_order_service.domain.entity.OrderStatus;
import com.sansarch.bookstore_order_service.infra.dto.PlaceOrderInputDtoBook;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderCreatedEvent extends Event {
    private final Long orderId;
    private final OrderStatus status;
    private final List<PlaceOrderInputDtoBook> items;

    public OrderCreatedEvent(Long orderId, OrderStatus status, List<PlaceOrderInputDtoBook> items) {
        super(orderId);
        this.orderId = orderId;
        this.status = status;
        this.items = items;
    }
}
