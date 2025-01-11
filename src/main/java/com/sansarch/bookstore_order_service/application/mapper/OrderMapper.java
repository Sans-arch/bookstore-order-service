package com.sansarch.bookstore_order_service.application.mapper;

import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.infra.order.dto.PlaceOrderOutputDtoBook;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "bookId", source = "bookId")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    PlaceOrderOutputDtoBook orderItemToPlaceOrderOutputDtoBook(OrderItem orderItem);

    @Mapping(target = "orderId", source = "id")
    PlaceOrderUseCaseOutputDto entityToPlaceOrderOutputDto(Order order);
}
