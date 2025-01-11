package com.sansarch.bookstore_order_service.application.usecase.place_order;

import com.sansarch.bookstore_order_service.application.repository.OrderRepository;
import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.domain.common.messaging.MessagePublisher;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.infra.messaging.RabbitMQConfiguration;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class PlaceOrderUseCase implements UseCase<PlaceOrderUseCaseInputDto, PlaceOrderUseCaseOutputDto> {

    private OrderRepository orderRepository;
    private MessagePublisher messagePublisher;

    @Override
    public PlaceOrderUseCaseOutputDto execute(PlaceOrderUseCaseInputDto input) {
        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .totalPrice(BigDecimal.ZERO)
                .build();

        orderRepository.save(order);

        var orderCreatedEvent = new OrderCreatedEvent(order.getId(), OrderStatus.PENDING, input.getItems());
        messagePublisher.publish(orderCreatedEvent, RabbitMQConfiguration.ORDER_EXCHANGE);

        return PlaceOrderUseCaseOutputDto.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .message("Order placed successfully")
                .build();
    }
}
