package com.sansarch.bookstore_order_service.application.service;

import com.sansarch.bookstore_order_service.application.mapper.OrderMapper;
import com.sansarch.bookstore_order_service.application.usecase.place_order.PlaceOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.application.usecase.process_order.ProcessOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_order.dto.ProcessOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.RetrieveOrderByIdUseCase;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto.RetrieveOrderByIdUseCaseInputDto;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.infra.order.http.dto.GetOrderResponseDto;
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

    public GetOrderResponseDto retrieveOrderById(Long id) {
        var output = retrieveOrderByIdUseCase.execute(new RetrieveOrderByIdUseCaseInputDto(id));
        var order = output.getOrder();
        return GetOrderResponseDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt().toString())
                .items(OrderMapper.INSTANCE.orderItemListToGetOrderResponseOrderItemDtoList(order.getItems()))
                .build();
    }
}
