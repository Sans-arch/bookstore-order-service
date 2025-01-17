package com.sansarch.bookstore_order_service.domain.order.event.consumer;

import com.sansarch.bookstore_order_service.application.service.OrderService;
import com.sansarch.bookstore_order_service.domain.order.event.StockCheckEvent;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderConsumer {
    private OrderService orderService;

    @RabbitListener(queues = "order-created")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        orderService.processCreatedOrder(event);
    }

    @RabbitListener(queues = "stock-check")
    public void handleStockCheckEvent(StockCheckEvent event) {
        orderService.checkStockAvailability(event);
    }
}
