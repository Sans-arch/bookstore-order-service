package com.sansarch.bookstore_order_service.domain.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class Order {

    private Long id;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<OrderItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void addItems(List<OrderItem> items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        totalPrice = items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void confirmOrder() {
        status = OrderStatus.CONFIRMED;
        updatedAt = LocalDateTime.now();
    }

    public void cancelOrder() {
        status = OrderStatus.CANCELLED;
        updatedAt = LocalDateTime.now();
    }
}
