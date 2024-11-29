package com.sansarch.bookstore_order_service.service;

import com.sansarch.bookstore_order_service.clients.CatalogServiceClient;
import com.sansarch.bookstore_order_service.clients.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.dto.PlaceOrderInputDtoBook;
import com.sansarch.bookstore_order_service.dto.PlaceOrderInputDto;
import com.sansarch.bookstore_order_service.dto.PlaceOrderOutputDto;
import com.sansarch.bookstore_order_service.entity.Order;
import com.sansarch.bookstore_order_service.entity.OrderItem;
import com.sansarch.bookstore_order_service.entity.OrderStatus;
import com.sansarch.bookstore_order_service.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.exception.BookUnavailableStockException;
import com.sansarch.bookstore_order_service.mapper.OrderMapper;
import com.sansarch.bookstore_order_service.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CatalogServiceClient catalogServiceClient;

    public PlaceOrderOutputDto placeOrder(PlaceOrderInputDto input) {
        List<OrderItem> orderItems = fetchItems(input.getItems());
        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .userId(input.getUserId())
                .status(OrderStatus.PENDING)
                .totalPrice(totalPrice)
                .build();

        orderItems.forEach(order::addItem);
        orderRepository.save(order);

        return OrderMapper.INSTANCE.entityToPlaceOrderOutputDto(order);
    }

    private List<OrderItem> fetchItems(List<PlaceOrderInputDtoBook> items) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (PlaceOrderInputDtoBook item : items) {
            var response = catalogServiceClient.getBookData(item.getBookId());
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BookNotFoundException(item.getBookId());
            }

            CatalogBookDto bookData = response.getBody();
            if (bookData.getStockAvailability() < item.getQuantity()) {
                throw new BookUnavailableStockException(item.getBookId());
            }

            var orderItem = OrderItem.builder()
                    .bookId(bookData.getId())
                    .quantity(item.getQuantity())
                    .price(bookData.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        return orderItems;
    }
}
