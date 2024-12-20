package com.sansarch.bookstore_order_service.domain.event.consumer;

import com.sansarch.bookstore_order_service.domain.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.domain.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class OrderConsumer {
    private OrderService orderService;

    @RabbitListener(queues = "order-created")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        log.info("Processing order {}", event.getOrderId());
        orderService.processOrder(event);
    }
}
