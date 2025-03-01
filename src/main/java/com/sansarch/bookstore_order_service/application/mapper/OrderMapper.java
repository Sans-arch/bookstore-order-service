package com.sansarch.bookstore_order_service.application.mapper;

import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderItem;
import com.sansarch.bookstore_order_service.infra.order.http.dto.get_order.GetOrderResponseOrderItemDto;
import com.sansarch.bookstore_order_service.infra.order.http.dto.place_order.PlaceOrderRequestDto;
import com.sansarch.bookstore_order_service.infra.order.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderModel orderEntityToOrderModel(Order order);

    Order orderModelToOrderEntity(OrderModel orderModel);

    List<GetOrderResponseOrderItemDto> orderItemListToGetOrderResponseOrderItemDtoList(List<OrderItem> orderItems);

    PlaceOrderUseCaseInputDto placeOrderRequestDtoToPlaceOrderUseCaseInputDto(PlaceOrderRequestDto placeOrderRequestDto);
}
