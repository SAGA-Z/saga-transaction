package org.sagaz.purchase.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.domain.PurchaseConfirmedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PurchaseConfirmedEventListener {
    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener
    public void handle(PurchaseConfirmedEvent event) {
        rabbitTemplate.convertAndSend("PurchaseConfirmedEvent", "#", event);
    }
}
