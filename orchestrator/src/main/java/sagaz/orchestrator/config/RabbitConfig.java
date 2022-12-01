package sagaz.orchestrator.config;

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
    Queue purchaseConfirmedEventQueue() {
        return new Queue("PurchaseConfirmedEvent.payment", false);
    }

    @Bean
    Queue paymentCreatedSubscribingFailedEventQueue()
    {
        return new Queue("PaymentCreatedSubscribingFailedEvent.payment", false);
    }
    @Bean
    TopicExchange purchaseConfirmedEventTopicExchange() {
        return new TopicExchange("PurchaseConfirmedEvent");
    }

    @Bean
    TopicExchange purchaseConfirmedSubscribingFailedEventTopicExchange() {
        return new TopicExchange("PurchaseConfirmedSubscribingFailedEvent");
    }

    @Bean
    TopicExchange paymentCreatedSubscribingFailedEventTopicExchange() {
        return new TopicExchange("PaymentCreatedSubscribingFailedEvent");
    }

    @Bean
    TopicExchange paymentCreatedEventTopicExchange() {
        return new TopicExchange("PaymentCreatedEvent");
    }

    @Bean
    Binding purchaseConfirmedEventBinding() {
        return BindingBuilder
                .bind(this.purchaseConfirmedEventQueue())
                .to(this.purchaseConfirmedEventTopicExchange()).with("#");
    }

    @Bean
    Binding paymentCreatedSubscribingFailedEventBinding() {
        return BindingBuilder
                .bind(this.paymentCreatedSubscribingFailedEventQueue())
                .to(this.paymentCreatedSubscribingFailedEventTopicExchange()).with("#");
    }

    @Bean
    RabbitAdmin rabbitAdmin(
            ConnectionFactory connectionFactory
    ) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(this.purchaseConfirmedEventTopicExchange());
        rabbitAdmin.declareExchange(this.purchaseConfirmedSubscribingFailedEventTopicExchange());
        rabbitAdmin.declareExchange(this.paymentCreatedEventTopicExchange());
        rabbitAdmin.declareExchange(this.paymentCreatedSubscribingFailedEventTopicExchange());
        rabbitAdmin.declareQueue(this.purchaseConfirmedEventQueue());
        rabbitAdmin.declareQueue(this.paymentCreatedSubscribingFailedEventQueue());
        rabbitAdmin.declareBinding(this.purchaseConfirmedEventBinding());
        rabbitAdmin.declareBinding(this.paymentCreatedSubscribingFailedEventBinding());
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
