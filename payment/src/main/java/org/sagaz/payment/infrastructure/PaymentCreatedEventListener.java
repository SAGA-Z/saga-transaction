package org.sagaz.payment.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.payment.domain.PaymentCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentCreatedEventListener {
    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener
    public void handle(PaymentCreatedEvent event) {
        //rabbitTemplate.convertAndSend("PaymentCreatedEvent", "#", event);
    }
}
