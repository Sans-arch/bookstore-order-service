package com.sansarch.bookstore_order_service.service;

import com.sansarch.bookstore_order_service.dto.PlaceOrderInputDto;
import com.sansarch.bookstore_order_service.dto.PlaceOrderOutputDto;
import com.sansarch.bookstore_order_service.entity.Order;
import com.sansarch.bookstore_order_service.mapper.OrderMapper;
import com.sansarch.bookstore_order_service.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;

    public PlaceOrderOutputDto placeOrder(PlaceOrderInputDto input) {
        Order order = new Order();
        order.setUserId(input.getUserId());

        // antes de mandar criar a ordem, ele vai ter que fazer uma consulta Feign lá pro catalog-service para pegar
        // as infos do livro

        input.getItems().stream()
                .map(OrderMapper.INSTANCE::placeOrderDtoBookToOrderItemEntity)
                .forEach(order::addItem);
        orderRepository.save(order);

        return OrderMapper.INSTANCE.orderEntityToPlaceOrderOutputDto(order);
    }
}
