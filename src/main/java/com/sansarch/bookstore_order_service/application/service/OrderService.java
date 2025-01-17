package com.sansarch.bookstore_order_service.application.service;

import com.sansarch.bookstore_order_service.application.mapper.OrderMapper;
import com.sansarch.bookstore_order_service.application.usecase.check_stock.CheckStockUseCase;
import com.sansarch.bookstore_order_service.application.usecase.check_stock.dto.CheckStockUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.PlaceOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_created_order.ProcessCreatedOrderUseCase;
import com.sansarch.bookstore_order_service.application.usecase.process_created_order.dto.ProcessCreatedOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.RetrieveOrderByIdUseCase;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto.RetrieveOrderByIdUseCaseInputDto;
import com.sansarch.bookstore_order_service.domain.order.event.OrderCreatedEvent;
import com.sansarch.bookstore_order_service.domain.order.event.StockCheckEvent;
import com.sansarch.bookstore_order_service.infra.order.http.dto.get_order.GetOrderResponseDto;
import com.sansarch.bookstore_order_service.infra.order.http.dto.place_order.PlaceOrderRequestDto;
import com.sansarch.bookstore_order_service.infra.order.http.dto.place_order.PlaceOrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private PlaceOrderUseCase placeOrderUseCase;
    private ProcessCreatedOrderUseCase processCreatedOrderUseCase;
    private RetrieveOrderByIdUseCase retrieveOrderByIdUseCase;
    private CheckStockUseCase checkStockUseCase;

    public PlaceOrderResponseDto placeOrder(PlaceOrderRequestDto input) {
        var output = placeOrderUseCase.execute(
                OrderMapper.INSTANCE.placeOrderRequestDtoToPlaceOrderUseCaseInputDto(input)
        );
        return PlaceOrderResponseDto.builder()
                .orderId(output.getOrderId())
                .message(output.getMessage())
                .status(output.getStatus())
                .build();
    }

    public void processCreatedOrder(OrderCreatedEvent event) {
        processCreatedOrderUseCase.execute(new ProcessCreatedOrderUseCaseInputDto(event));
    }

    public void checkStockAvailability(StockCheckEvent event) {
        checkStockUseCase.execute(new CheckStockUseCaseInputDto(event));
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
