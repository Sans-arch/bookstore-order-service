package com.sansarch.bookstore_order_service.application.usecase.process_order;

import com.sansarch.bookstore_order_service.application.repository.OrderRepository;
import com.sansarch.bookstore_order_service.application.service.OrderService;
import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputBookDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order.dto.ProcessOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order.dto.ProcessOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderItem;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderStatus;
import com.sansarch.bookstore_order_service.domain.order.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.infra.http.clients.catalog.CatalogService;
import com.sansarch.bookstore_order_service.infra.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.order.dto.DeductStockDto;
import feign.RetryableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ProcessOrderUseCase implements UseCase<ProcessOrderUseCaseInputDto, ProcessOrderUseCaseOutputDto> {

    private OrderRepository orderRepository;
    private CatalogService catalogService;
    private OrderService orderService;

    @Override
    public ProcessOrderUseCaseOutputDto execute(ProcessOrderUseCaseInputDto input) {
        var event = input.getEvent();
        var order = orderService.retrieveOrderById(event.getOrderId());

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

        return new ProcessOrderUseCaseOutputDto();
    }

    private List<OrderItem> processBooksPurchase(List<PlaceOrderUseCaseInputBookDto> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (PlaceOrderUseCaseInputBookDto item : items) {
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
