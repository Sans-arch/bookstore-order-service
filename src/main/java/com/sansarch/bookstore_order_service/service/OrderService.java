package com.sansarch.bookstore_order_service.service;

import com.sansarch.bookstore_order_service.dto.DeductStockDto;
import com.sansarch.bookstore_order_service.dto.PlaceOrderInputDto;
import com.sansarch.bookstore_order_service.dto.PlaceOrderInputDtoBook;
import com.sansarch.bookstore_order_service.dto.PlaceOrderOutputDto;
import com.sansarch.bookstore_order_service.entity.Order;
import com.sansarch.bookstore_order_service.entity.OrderItem;
import com.sansarch.bookstore_order_service.entity.OrderStatus;
import com.sansarch.bookstore_order_service.exception.BookNotFoundException;
import com.sansarch.bookstore_order_service.exception.UnavailableBookInStockException;
import com.sansarch.bookstore_order_service.http.clients.catalog.CatalogService;
import com.sansarch.bookstore_order_service.http.clients.catalog.dto.CatalogBookDto;
import com.sansarch.bookstore_order_service.mapper.OrderMapper;
import com.sansarch.bookstore_order_service.repository.OrderRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CatalogService catalogService;

    public PlaceOrderOutputDto placeOrder(PlaceOrderInputDto input) {
        Order order = Order.builder()
                .userId(input.getUserId())
                .status(OrderStatus.PENDING)
                .build();

        checkStock(input.getItems());
        List<OrderItem> orderedBooks = processBooksPurchase(input.getItems());
        order.addItems(orderedBooks);
        order.calculateTotalPrice();
        order.setStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);
        return OrderMapper.INSTANCE.entityToPlaceOrderOutputDto(order);
    }

    private void checkStock(List<PlaceOrderInputDtoBook> items) {
        for (PlaceOrderInputDtoBook item : items) {
            try {
                var response = catalogService.getBookData(item.getBookId());
                CatalogBookDto bookData = Objects.requireNonNull(response.getBody());

                if (bookData.getStockAvailability() < item.getQuantity()) {
                    throw new UnavailableBookInStockException(item.getBookId());
                }
            } catch (FeignException e) {
                if (e.status() == HttpStatus.NOT_FOUND.value()) {
                    throw new BookNotFoundException(item.getBookId());
                }
            }
        }
    }

    private List<OrderItem> processBooksPurchase(List<PlaceOrderInputDtoBook> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (PlaceOrderInputDtoBook item : items) {
            var response = catalogService.getBookData(item.getBookId());
            CatalogBookDto bookData = response.getBody();

            if (bookData == null) {
                throw new BookNotFoundException(item.getBookId());
            }

            var orderItem = OrderItem.builder()
                    .bookId(bookData.getId())
                    .quantity(item.getQuantity())
                    .price(bookData.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        List<DeductStockDto> itensToDeduct = items.stream()
                .map(item -> new DeductStockDto(item.getBookId(), item.getQuantity()))
                .toList();
        catalogService.deductStock(itensToDeduct);
        return orderItems;
    }
}
