package com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id;

import com.sansarch.bookstore_order_service.application.repository.OrderRepository;
import com.sansarch.bookstore_order_service.application.usecase.UseCase;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto.RetrieveOrderByIdUseCaseInputDto;
import com.sansarch.bookstore_order_service.application.usecase.retrieve_order_by_id.dto.RetrieveOrderByIdUseCaseOutputDto;
import com.sansarch.bookstore_order_service.domain.order.exception.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RetrieveOrderByIdUseCase implements UseCase<RetrieveOrderByIdUseCaseInputDto, RetrieveOrderByIdUseCaseOutputDto> {

    private OrderRepository orderRepository;

    @Override
    public RetrieveOrderByIdUseCaseOutputDto execute(RetrieveOrderByIdUseCaseInputDto input) {
        var order = orderRepository.findById(input.getId())
                .orElseThrow(() -> new OrderNotFoundException(input.getId()));
        return new RetrieveOrderByIdUseCaseOutputDto(order);
    }
}
