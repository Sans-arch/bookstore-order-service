package com.sansarch.bookstore_order_service.infra.http.controller;

import com.sansarch.bookstore_order_service.infra.order.dto.PlaceOrderInputDto;
import com.sansarch.bookstore_order_service.infra.order.dto.PlaceOrderOutputDto;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.application.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<PlaceOrderOutputDto> placeOrder(@RequestBody PlaceOrderInputDto input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(input));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.retrieveOrderById(id));
    }
}
