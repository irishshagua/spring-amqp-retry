package com.mooneyserver.springamqpretry;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AmqpRetryApp implements RabbitListenerConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AmqpRetryApp.class, args);
    }

    @Bean
    MessageConverter msgConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    MessageHandlerMethodFactory methodHandlerFactory(MessageConverter msgConverter) {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(msgConverter);
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(methodHandlerFactory(msgConverter()));
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable("my-consuming-q").build();
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange("some-fanout-exchange");
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(workMessagesRetryInterceptor());
        return factory;
    }

    @Bean
    public SimpleRetryPolicy rejectionRetryPolicy(){
        Map<Class<? extends Throwable> , Boolean> exceptionsMap = new HashMap<>();
        exceptionsMap.put(IllegalStateException.class, true);
        exceptionsMap.put(ArithmeticException.class, false);

        return new SimpleRetryPolicy(5 , exceptionsMap, true);
    }

    @Bean
    public RetryOperationsInterceptor workMessagesRetryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .retryPolicy(rejectionRetryPolicy())
                .backOffOptions(1000, 2, 10000)
                .build();
    }
}
