package com.sansarch.bookstore_order_service.infra.messaging;

import com.sansarch.bookstore_order_service.domain.entity.Order;
import com.sansarch.bookstore_order_service.domain.messaging.MessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class RabbitMQMessagePublisher implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Order order) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.ORDER_EXCHANGE,
                "",
                order);
        log.info("Order created message published: {}", order.getId());
    }
}
