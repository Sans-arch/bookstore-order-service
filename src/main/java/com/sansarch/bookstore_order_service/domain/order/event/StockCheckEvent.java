package com.sansarch.bookstore_order_service.domain.order.event;

import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputBookDto;
import com.sansarch.bookstore_order_service.domain.common.messaging.Event;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class StockCheckEvent implements Event {
    private final Long orderId;
    private final List<PlaceOrderUseCaseInputBookDto> items;

    public StockCheckEvent(Long orderId, List<PlaceOrderUseCaseInputBookDto> items) {
        this.orderId = orderId;
        this.items = items;
    }

    @Override
    public String getName() {
        return StockCheckEvent.class.getName();
    }
}
