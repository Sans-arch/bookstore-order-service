package com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed;

import com.sansarch.bookstore_order_service.domain.common.messaging.Event;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed.dto.OrderStockConfirmedBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class OrderStockConfirmedEvent implements Event {
    private final Long orderId;
    private final List<OrderStockConfirmedBook> items;

    @Override
    public String getName() {
        return OrderStockConfirmedEvent.class.getName();
    }
}
