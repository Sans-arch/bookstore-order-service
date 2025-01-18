package com.sansarch.bookstore_order_service.application.usecase.process_order_stock_confirmed;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_confirmed.dto.ProcessOrderStockConfirmedInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_confirmed.dto.ProcessOrderStockConfirmedOutputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderItem;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed.OrderStockConfirmedEvent;
import com.sansarch.bookstore_order_service.domain.order.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.domain.order.exception.OrderNotFoundException;
import com.sansarch.bookstore_order_service.domain.order.repository.OrderRepository;
import com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.CatalogServiceClient;
import com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.order.dto.DeductStockDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ProcessOrderStockConfirmedUseCase implements UseCase<ProcessOrderStockConfirmedInputDto,
        ProcessOrderStockConfirmedOutputDto> {

    private CatalogServiceClient catalogServiceClient;
    private OrderRepository orderRepository;

    @Override
    public ProcessOrderStockConfirmedOutputDto execute(ProcessOrderStockConfirmedInputDto input) {
        OrderStockConfirmedEvent event = input.getEvent();
        List<OrderItem> orderItems = new ArrayList<>();

        log.info("Processing order with stock items's stock availability confirmed with ID: {}",
                event.getOrderId());

        for (var item : event.getItems()) {
            var response = catalogServiceClient.getBookData(item.getBookId());
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

        catalogServiceClient.deductStock(itemsToDeduct);

        Order order =
                orderRepository.findById(event.getOrderId()).orElseThrow(() -> new OrderNotFoundException(event.getOrderId()));
        order.addItems(orderItems);
        order.confirmOrder();

        orderRepository.save(order);
        return new ProcessOrderStockConfirmedOutputDto();
    }
}
