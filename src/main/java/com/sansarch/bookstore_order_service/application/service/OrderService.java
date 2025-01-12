package com.sansarch.bookstore_order_service.application.service;

import com.sansarch.bookstore_order_service.application.repository.OrderRepository;
import com.sansarch.bookstore_order_service.application.usecase.place_order.PlaceOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order.ProcessOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_order.dto.ProcessOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.domain.order.exception.OrderNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private PlaceOrderUseCase placeOrderUseCase;
    private ProcessOrderUseCase processOrderUseCase;

    public PlaceOrderUseCaseOutputDto placeOrder(PlaceOrderUseCaseInputDto input) {
        return placeOrderUseCase.execute(input);
    }

    public void processOrder(OrderCreatedEvent event) {
        processOrderUseCase.execute(new ProcessOrderUseCaseInputDto(event));
    }

    public Order retrieveOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
