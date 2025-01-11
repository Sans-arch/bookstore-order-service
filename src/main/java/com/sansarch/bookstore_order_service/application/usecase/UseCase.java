package com.sansarch.bookstore_order_service.application.usecase;

public interface UseCase<I, O> {
    O execute(I input);
}
