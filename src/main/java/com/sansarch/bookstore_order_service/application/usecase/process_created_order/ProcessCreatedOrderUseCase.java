package com.sansarch.bookstore_order_service.application.usecase.process_created_order;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputBookDto;
import com.sansarch.bookstore_order_service.application.usecase.process_created_order.dto.ProcessCreatedOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_created_order.dto.ProcessCreatedOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.RetrieveOrderByIdUseCase;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto.RetrieveOrderByIdUseCaseInputDto;
import com.sansarch.bookstore_order_service.domain.common.messaging.MessagePublisher;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderItem;
import com.sansarch.bookstore_order_service.domain.order.event.StockCheckEvent;
import com.sansarch.bookstore_order_service.domain.order.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.CatalogServiceClient;
import com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.infra.order.dto.DeductStockDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sansarch.bookstore_order_service.infra.messaging.RabbitMQConfiguration.ORDER_EXCHANGE;
import static com.sansarch.bookstore_order_service.infra.messaging.RabbitMQConfiguration.STOCK_CHECK_QUEUE;

@Slf4j
@AllArgsConstructor
@Component
public class ProcessCreatedOrderUseCase implements UseCase<ProcessCreatedOrderUseCaseInputDto,
        ProcessCreatedOrderUseCaseOutputDto> {

    private MessagePublisher messagePublisher;
    private CatalogServiceClient catalogServiceClient;
    private RetrieveOrderByIdUseCase retrieveOrderByIdUseCase;

    @Override
    @Transactional
    public ProcessCreatedOrderUseCaseOutputDto execute(ProcessCreatedOrderUseCaseInputDto input) {
        var event = input.getEvent();
        log.info("Processing order with ID: {}", event.getOrderId());

        var order = retrieveOrderByIdUseCase.execute(
                new RetrieveOrderByIdUseCaseInputDto(event.getOrderId())).getOrder();

        StockCheckEvent stockCheckEvent = new StockCheckEvent(order.getId(), event.getItems());
        messagePublisher.publish(stockCheckEvent, ORDER_EXCHANGE, "stock.check");

        return new ProcessCreatedOrderUseCaseOutputDto();
    }

    private List<OrderItem> processBooksPurchase(List<PlaceOrderUseCaseInputBookDto> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (PlaceOrderUseCaseInputBookDto item : items) {
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
        return orderItems;
    }
}
