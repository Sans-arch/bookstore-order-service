package com.sansarch.bookstore_order_service.application.usecase.process_order_stock_failed;

import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_failed.dto.ProcessOrderStockFailedInputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order_stock_failed.dto.ProcessOrderStockFailedOutputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.event.order_stock_failed.OrderStockFailedEvent;
import com.sansarch.bookstore_order_service.domain.order.exception.OrderNotFoundException;
import com.sansarch.bookstore_order_service.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ProcessOrderStockFailedUseCase implements UseCase<ProcessOrderStockFailedInputDto,
        ProcessOrderStockFailedOutputDto> {

    private OrderRepository orderRepository;

    @Override
    public ProcessOrderStockFailedOutputDto execute(ProcessOrderStockFailedInputDto input) {
        OrderStockFailedEvent event = input.getEvent();

        log.info("Processing order with stock items's stock availability unavailable with ID: {}",
                event.getOrderId());

        Order order =
                orderRepository.findById(event.getOrderId()).orElseThrow(() -> new OrderNotFoundException(event.getOrderId()));

        order.cancelOrder();
        orderRepository.save(order);
        return new ProcessOrderStockFailedOutputDto();
    }
}
