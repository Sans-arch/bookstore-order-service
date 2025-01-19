package com.sansarch.bookstore_order_service.infra.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String ORDER_EXCHANGE = "order-exchange";
    public static final String ORDER_CREATED_QUEUE = "order-created";
    public static final String STOCK_CHECK_QUEUE = "stock-check";
    public static final String STOCK_CHECK_CONFIRMED_QUEUE = "stock-check-confirmed";
    public static final String STOCK_CHECK_FAILED_QUEUE = "stock-check-failed";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public MessageConverter createJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(createJackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true);
    }

    @Bean
    public Queue stockCheckQueue() {
        return new Queue(STOCK_CHECK_QUEUE, true);
    }

    @Bean
    public Queue stockCheckConfirmedQueue() {
        return new Queue(STOCK_CHECK_CONFIRMED_QUEUE, true);
    }

    @Bean
    public Queue stockCheckFailedQueue() {
        return new Queue(STOCK_CHECK_FAILED_QUEUE, true);
    }

    @Bean
    public Binding bindingOrderCreatedQueueToOrderExchange(Queue orderCreatedQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(orderExchange).with("order.created");
    }

    @Bean
    public Binding bindingStockCheckQueueToOrderExchange(Queue stockCheckQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(stockCheckQueue).to(orderExchange).with("stock.check");
    }

    @Bean
    public Binding bindingStockCheckConfirmedQueueToOrderExchange(Queue stockCheckConfirmedQueue,
                                                                  TopicExchange orderExchange) {
        return BindingBuilder.bind(stockCheckConfirmedQueue).to(orderExchange).with("stock.check.confirmed");
    }

    @Bean
    public Binding bindingStockCheckFailedQueueToOrderExchange(Queue stockCheckFailedQueue,
                                                              TopicExchange orderExchange) {
        return BindingBuilder.bind(stockCheckFailedQueue).to(orderExchange).with("stock.check.failed");
    }
}
