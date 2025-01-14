package com.sansarch.bookstore_order_service.application.service;

import com.sansarch.bookstore_order_service.application.usecase.place_order.PlaceOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order.ProcessOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_order.dto.ProcessOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.RetrieveOrderByIdUseCase;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto.RetrieveOrderByIdUseCaseInputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private PlaceOrderUseCase placeOrderUseCase;
    private ProcessOrderUseCase processOrderUseCase;
    private RetrieveOrderByIdUseCase retrieveOrderByIdUseCase;

    public PlaceOrderUseCaseOutputDto placeOrder(PlaceOrderUseCaseInputDto input) {
        return placeOrderUseCase.execute(input);
    }

    public void processOrder(OrderCreatedEvent event) {
        processOrderUseCase.execute(new ProcessOrderUseCaseInputDto(event));
    }

    public Order retrieveOrderById(Long id) {
        var output = retrieveOrderByIdUseCase.execute(new RetrieveOrderByIdUseCaseInputDto(id));
        return output.getOrder();
    }
}
