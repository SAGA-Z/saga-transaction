package org.sagaz.delivery.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.delivery.domain.DeliveringEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DeliveringEventListener {
    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener
    public void handle(DeliveringEvent event) {
        rabbitTemplate.convertAndSend("DeliveringEvent", "#", event);
    }
}
