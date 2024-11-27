package com.sansarch.bookstore_order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookstoreOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreOrderServiceApplication.class, args);
	}

}
