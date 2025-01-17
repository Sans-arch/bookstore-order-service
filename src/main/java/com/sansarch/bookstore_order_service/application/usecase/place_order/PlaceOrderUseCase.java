package com.sansarch.bookstore_order_service.application.usecase.place_order;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.domain.common.messaging.MessagePublisher;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.domain.order.repository.OrderRepository;
import com.sansarch.bookstore_order_service.infra.messaging.RabbitMQConfiguration;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@Component
public class PlaceOrderUseCase implements UseCase<PlaceOrderUseCaseInputDto, PlaceOrderUseCaseOutputDto> {

    private OrderRepository orderRepository;
    private MessagePublisher messagePublisher;

    @Override
    @Transactional
    public PlaceOrderUseCaseOutputDto execute(PlaceOrderUseCaseInputDto input) {
        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .totalPrice(BigDecimal.ZERO)
                .build();
        order = orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order.getId(), order.getStatus(), input.getItems());
        messagePublisher.publish(orderCreatedEvent, RabbitMQConfiguration.ORDER_EXCHANGE, "order.created");

        log.info("Order {} placed successfully", order.getId());
        return PlaceOrderUseCaseOutputDto.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .message("Order placed successfully")
                .build();
    }
}
