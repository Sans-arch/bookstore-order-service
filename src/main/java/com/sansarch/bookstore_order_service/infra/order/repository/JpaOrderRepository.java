package com.sansarch.bookstore_order_service.infra.order.repository;

import com.sansarch.bookstore_order_service.application.mapper.OrderMapper;
import com.sansarch.bookstore_order_service.domain.order.entity.Order;
import com.sansarch.bookstore_order_service.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataOrderRepository springDataRepository;

    @Override
    public Order save(Order order) {
        var persistedOrder = springDataRepository.save(OrderMapper.INSTANCE.orderEntityToOrderModel(order));
        return OrderMapper.INSTANCE.orderModelToOrderEntity(persistedOrder);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return springDataRepository.findById(id).map(OrderMapper.INSTANCE::orderModelToOrderEntity);
    }
}
