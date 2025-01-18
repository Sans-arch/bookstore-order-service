package com.sansarch.bookstore_order_service.application.usecase.process_created_order;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_created_order.dto.ProcessCreatedOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_created_order.dto.ProcessCreatedOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.RetrieveOrderByIdUseCase;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto.RetrieveOrderByIdUseCaseInputDto;
import com.sansarch.bookstore_order_service.domain.common.messaging.MessagePublisher;
import com.sansarch.bookstore_order_service.domain.order.event.StockCheckEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.sansarch.bookstore_order_service.infra.messaging.RabbitMQConfiguration.ORDER_EXCHANGE;

@Slf4j
@AllArgsConstructor
@Component
public class ProcessCreatedOrderUseCase implements UseCase<ProcessCreatedOrderUseCaseInputDto,
        ProcessCreatedOrderUseCaseOutputDto> {

    private MessagePublisher messagePublisher;
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
}
