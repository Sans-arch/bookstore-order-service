package com.sansarch.bookstore_order_service.mapper;

import com.sansarch.bookstore_order_service.dto.PlaceOrderOutputDto;
import com.sansarch.bookstore_order_service.dto.PlaceOrderOutputDtoBook;
import com.sansarch.bookstore_order_service.entity.Order;
import com.sansarch.bookstore_order_service.entity.OrderItem;
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
    PlaceOrderOutputDto entityToPlaceOrderOutputDto(Order order);
}
