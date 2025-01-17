package com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed;

import com.sansarch.bookstore_order_service.domain.common.messaging.Event;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed.dto.OrderStockFailedBook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Data
@ToString
public class OrderStockFailedEvent implements Event {
    private final Long orderId;
    private final List<OrderStockFailedBook> items;

    @Override
    public String getName() {
        return OrderStockFailedEvent.class.getName();
    }
}
