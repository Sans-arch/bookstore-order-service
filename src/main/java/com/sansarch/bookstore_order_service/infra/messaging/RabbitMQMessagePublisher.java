package com.sansarch.bookstore_order_service.infra.messaging;

import com.sansarch.bookstore_order_service.domain.entity.Order;
import com.sansarch.bookstore_order_service.domain.messaging.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMQMessagePublisher implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishOrderCreated(Order order) {
        rabbitTemplate.convertAndSend("orders-exchange", "order.created", order);
    }
}
