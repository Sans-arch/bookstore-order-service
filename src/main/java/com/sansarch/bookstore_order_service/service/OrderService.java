package com.sansarch.bookstore_order_service.service;

import com.sansarch.bookstore_order_service.clients.CatalogServiceClient;
import com.sansarch.bookstore_order_service.dto.PlaceOrderDtoBook;
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
    private CatalogServiceClient catalogServiceClient;

    public PlaceOrderOutputDto placeOrder(PlaceOrderInputDto input) {
        Order order = new Order();
        order.setUserId(input.getUserId());

        input.getItems().stream()
                .map(PlaceOrderDtoBook::getBookId)
                .forEach(id -> {
                    var response = catalogServiceClient.getBookData(id);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        var bookData = response.getBody();
                        order.addItem(OrderMapper.INSTANCE.bookDataDtoToOrderItemEntity(bookData));
                    }
                });

        return OrderMapper.INSTANCE.orderEntityToPlaceOrderOutputDto(order);
    }

}
