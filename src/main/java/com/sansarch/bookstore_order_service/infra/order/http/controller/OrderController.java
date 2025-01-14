package com.sansarch.bookstore_order_service.infra.order.http.controller;

import com.sansarch.bookstore_order_service.application.service.OrderService;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.place_order.dto.PlaceOrderUseCaseOutputDto;
import com.sansarch.bookstore_order_service.infra.order.http.dto.GetOrderResponseDto;
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
    public ResponseEntity<PlaceOrderUseCaseOutputDto> placeOrder(@RequestBody PlaceOrderUseCaseInputDto input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(input));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderResponseDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.retrieveOrderById(id));
    }
}
