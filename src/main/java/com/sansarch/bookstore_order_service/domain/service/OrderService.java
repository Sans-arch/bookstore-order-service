package com.sansarch.bookstore_order_service.domain.service;

import com.sansarch.bookstore_order_service.domain.entity.Order;
import com.sansarch.bookstore_order_service.domain.entity.OrderItem;
import com.sansarch.bookstore_order_service.domain.entity.OrderStatus;
import com.sansarch.bookstore_order_service.domain.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.domain.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.domain.exception.OrderNotFoundException;
import com.sansarch.bookstore_order_service.domain.exception.UnavailableBookInStockException;
import com.sansarch.bookstore_order_service.domain.messaging.MessagePublisher;
import com.sansarch.bookstore_order_service.domain.repository.OrderRepository;
import com.sansarch.bookstore_order_service.infra.dto.DeductStockDto;
import com.sansarch.bookstore_order_service.infra.dto.PlaceOrderInputDto;
import com.sansarch.bookstore_order_service.infra.dto.PlaceOrderInputDtoBook;
import com.sansarch.bookstore_order_service.infra.dto.PlaceOrderOutputDto;
import com.sansarch.bookstore_order_service.infra.http.clients.catalog.CatalogService;
import com.sansarch.bookstore_order_service.infra.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.messaging.RabbitMQConfiguration;
import feign.FeignException;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private CatalogService catalogService;
    private OrderRepository orderRepository;
    private MessagePublisher messagePublisher;

    public PlaceOrderOutputDto placeOrder(PlaceOrderInputDto input) {
        Order order = Order.builder()
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .totalPrice(BigDecimal.ZERO)
                .build();

        orderRepository.save(order);

        var orderCreatedEvent = new OrderCreatedEvent(order.getId(), OrderStatus.PENDING, input.getItems());
        messagePublisher.publish(orderCreatedEvent, RabbitMQConfiguration.ORDER_EXCHANGE);

        return PlaceOrderOutputDto.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .message("Order placed successfully")
                .build();
    }

    public void processOrder(OrderCreatedEvent event) {
        var order = retrieveOrderById(event.getOrderId());
        try {
            checkStock(event.getItems());
            List<OrderItem> orderItems = processBooksPurchase(event.getItems());
            order.addItems(orderItems);
            order.calculateTotalPrice();
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        } catch (RetryableException e) {
            // Todo: Criar uma DLQ para tratar esses casos (para não cancelar a ordem já q é um erro de network)
            log.error("Feign error during order processing: {}: {}", event.getOrderId(), e.getMessage());
        } catch (Exception e) {
            log.error("Error processing order {}: {}", event.getOrderId(), e.getMessage());
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        }
    }

    public Order retrieveOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    private void checkStock(List<PlaceOrderInputDtoBook> items) {
        for (PlaceOrderInputDtoBook item : items) {
            try {
                var response = catalogService.getBookData(item.getBookId());
                CatalogBookDto bookData = Objects.requireNonNull(response.getBody());

                if (bookData.getStockAvailability() < item.getQuantity()) {
                    throw new UnavailableBookInStockException(item.getBookId());
                }
            } catch (FeignException e) {
                if (e.status() == HttpStatus.NOT_FOUND.value()) {
                    throw new BookNotFoundException(item.getBookId());
                }
            }
        }
    }

    private List<OrderItem> processBooksPurchase(List<PlaceOrderInputDtoBook> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (PlaceOrderInputDtoBook item : items) {
            var response = catalogService.getBookData(item.getBookId());
            CatalogBookDto bookData = response.getBody();

            if (bookData == null) {
                throw new BookNotFoundException(item.getBookId());
            }

            var orderItem = OrderItem.builder()
                    .bookId(bookData.getId())
                    .quantity(item.getQuantity())
                    .price(bookData.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        List<DeductStockDto> itensToDeduct = items.stream()
                .map(item -> new DeductStockDto(item.getBookId(), item.getQuantity()))
                .toList();
        catalogService.deductStock(itensToDeduct);
        return orderItems;
    }
}
