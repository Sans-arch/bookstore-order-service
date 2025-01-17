package com.sansarch.bookstore_order_service.application.event.consumer;

import com.sansarch.bookstore_order_service.application.service.OrderService;
import com.sansarch.bookstore_order_service.domain.order.event.StockCheckEvent;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed.OrderStockConfirmedEvent;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed.OrderStockFailedEvent;
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

    @RabbitListener(queues = "stock-check-confirmed")
    public void handleOrderStockConfirmedEvent(OrderStockConfirmedEvent event) {
        orderService.processOrderWithConfirmedStock(event);
    }

    @RabbitListener(queues = "stock-check-failed")
    public void handleOrderStockFailedEvent(OrderStockFailedEvent event) {
        orderService.processOrderWithFailedStock(event);
    }
}
