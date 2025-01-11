package com.sansarch.bookstore_order_service.application.service;

import com.sansarch.bookstore_order_service.application.repository.OrderRepository;
import com.sansarch.bookstore_order_service.application.usecase.place_order.PlaceOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderItem;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.domain.order.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.domain.order.exception.OrderNotFoundException;
import com.sansarch.bookstore_order_service.infra.http.clients.catalog.CatalogService;
import com.sansarch.bookstore_order_service.infra.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.order.dto.DeductStockDto;
import com.sansarch.bookstore_order_service.infra.order.dto.PlaceOrderInputDtoBook;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private CatalogService catalogService;
    private OrderRepository orderRepository;
    private PlaceOrderUseCase placeOrderUseCase;

    public PlaceOrderUseCaseOutputDto placeOrder(PlaceOrderUseCaseInputDto input) {
        return placeOrderUseCase.execute(input);
    }

    public void processOrder(OrderCreatedEvent event) {
        var order = retrieveOrderById(event.getOrderId());

        try {
            catalogService.checkStockAvailability(event.getItems());

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

        List<DeductStockDto> itemsToDeduct = orderItems.stream()
                .map(item -> new DeductStockDto(item.getBookId(), item.getQuantity()))
                .toList();

        catalogService.deductStock(itemsToDeduct);
        return orderItems;
    }
}
