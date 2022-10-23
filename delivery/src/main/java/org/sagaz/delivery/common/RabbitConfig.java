package org.sagaz.delivery.common;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    Queue PaymentCreatedEventQueue() {
        return new Queue("PaymentCreatedEvent.delivery", false);
    }

    @Bean
    Queue deliveringSubscribingFailedEventQueue()
    {
        return new Queue("DeliveringSubscribingFailedEvent.delivery", false);
    }
    @Bean
    TopicExchange PaymentCreatedEventTopicExchange() {
        return new TopicExchange("PaymentCreatedEvent");
    }

    @Bean
    TopicExchange paymentCreatedSubscribingFailedEventTopicExchange() {
        return new TopicExchange("PaymentCreatedSubscribingFailedEvent");
    }

    @Bean
    TopicExchange deliveringSubscribingFailedEventTopicExchange() {
        return new TopicExchange("DeliveredSubscribingFailedEvent");
    }

    @Bean
    TopicExchange deliveringEventTopicExchange() {
        return new TopicExchange("DeliveringEvent");
    }

    @Bean
    Binding PaymentCreatedEventBinding() {
        return BindingBuilder
                .bind(this.PaymentCreatedEventQueue())
                .to(this.PaymentCreatedEventTopicExchange()).with("#");
    }

    @Bean
    Binding deliveringSubscribingFailedEventBinding() {
        return BindingBuilder
                .bind(this.deliveringSubscribingFailedEventQueue())
                .to(this.deliveringSubscribingFailedEventTopicExchange()).with("#");
    }

    @Bean
    RabbitAdmin rabbitAdmin(
            ConnectionFactory connectionFactory
    ) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(this.PaymentCreatedEventTopicExchange());
        rabbitAdmin.declareExchange(this.paymentCreatedSubscribingFailedEventTopicExchange());
        rabbitAdmin.declareExchange(this.deliveringEventTopicExchange());
        rabbitAdmin.declareExchange(this.deliveringSubscribingFailedEventTopicExchange());
        rabbitAdmin.declareQueue(this.PaymentCreatedEventQueue());
        rabbitAdmin.declareQueue(this.deliveringSubscribingFailedEventQueue());
        rabbitAdmin.declareBinding(this.PaymentCreatedEventBinding());
        rabbitAdmin.declareBinding(this.deliveringSubscribingFailedEventBinding());
        return rabbitAdmin;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
