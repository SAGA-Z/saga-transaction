package org.sagaz.delivery.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.delivery.application.DeliveryCreationService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class PurchaseConfirmedEventSubscriber {
    private final DeliveryCreationService deliveryCreationService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "PurchaseConfirmedEvent.delivery")
    @Transactional
    public void subscribe(final PurchaseConfirmedEvent event) {
        try {
            deliveryCreationService.create(event.purchaseUuid);
            if (event.errorMode) throw new Error("Manually created Error");

        } catch (Error error) {
            rabbitTemplate.convertAndSend("PurchaseConfirmedSubscribingFailedEvent", "#", event);
            throw new AmqpRejectAndDontRequeueException(error);
        }
    }
}
