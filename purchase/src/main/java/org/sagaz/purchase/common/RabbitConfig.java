package org.sagaz.purchase.common;

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
    Queue purchaseConfirmedSubscribingFailedEventQueue() {
        return new Queue("PurchaseConfirmedSubscribingFailedEvent.purchase", false);
    }
    @Bean
    TopicExchange purchaseConfirmedSubscribingFailedEventTopicExchange() {
        return new TopicExchange("PurchaseConfirmedSubscribingFailedEvent");
    }
    @Bean
    TopicExchange purchaseConfirmedEventTopicExchange() {
        return new TopicExchange("PurchaseConfirmedEvent");
    }

    @Bean
    Binding purchaseConfirmedSubscribingFailedEventBinding() {
        return BindingBuilder
                .bind(this.purchaseConfirmedSubscribingFailedEventQueue())
                .to(this.purchaseConfirmedSubscribingFailedEventTopicExchange()).with("#");
    }

    @Bean
    RabbitAdmin rabbitAdmin(
            ConnectionFactory connectionFactory
    ) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(this.purchaseConfirmedEventTopicExchange());
        rabbitAdmin.declareExchange(this.purchaseConfirmedSubscribingFailedEventTopicExchange());
        rabbitAdmin.declareQueue(this.purchaseConfirmedSubscribingFailedEventQueue());
        rabbitAdmin.declareBinding(this.purchaseConfirmedSubscribingFailedEventBinding());
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
