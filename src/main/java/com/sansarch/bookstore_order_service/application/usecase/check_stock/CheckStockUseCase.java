package com.sansarch.bookstore_order_service.application.usecase.check_stock;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.check_stock.dto.CheckStockUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.check_stock.dto.CheckStockUseCaseOutputDto;
import com.sansarch.bookstore_order_service.domain.common.messaging.MessagePublisher;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed.OrderStockConfirmedEvent;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_confirmed.dto.OrderStockConfirmedBook;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed.OrderStockFailedEvent;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed.dto.OrderStockFailedBook;
import com.sansarch.bookstore_order_service.infra.common.http.clients.catalog.CatalogServiceClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sansarch.bookstore_order_service.infra.messaging.RabbitMQConfiguration.ORDER_EXCHANGE;

@Slf4j
@AllArgsConstructor
@Component
public class CheckStockUseCase implements UseCase<CheckStockUseCaseInputDto, CheckStockUseCaseOutputDto> {

    private CatalogServiceClient catalogServiceClient;
    private MessagePublisher messagePublisher;

    @Override
    public CheckStockUseCaseOutputDto execute(CheckStockUseCaseInputDto input) {
        var event = input.getEvent();
        log.info("Checking stock for order with ID: {}: ", event.getOrderId());

        var response = catalogServiceClient.checkStockAvailability(event.getItems());
        var responseData = response.getBody();
        boolean areAllBooksAvailable = responseData.getAllAvailable();

        if (areAllBooksAvailable) {
            log.info("Stock check passed for order {}: ", event.getOrderId());

            List<OrderStockConfirmedBook> items = event.getItems().stream()
                    .map(item -> new OrderStockConfirmedBook(item.getBookId(), item.getQuantity())).toList();
            OrderStockConfirmedEvent orderStockConfirmedEvent = new OrderStockConfirmedEvent(event.getOrderId(), items);
            messagePublisher.publish(orderStockConfirmedEvent, ORDER_EXCHANGE, "stock.check.confirmed");
        } else {
            log.info("Stock check failed for order {}: ", event.getOrderId());

            List<OrderStockFailedBook> items = event.getItems().stream()
                    .map(item -> new OrderStockFailedBook(item.getBookId())).toList();
            OrderStockFailedEvent orderStockFailedEvent = new OrderStockFailedEvent(event.getOrderId(), items);
            messagePublisher.publish(orderStockFailedEvent, ORDER_EXCHANGE, "stock.check.failed");
        }

        return new CheckStockUseCaseOutputDto(areAllBooksAvailable);
    }
}
