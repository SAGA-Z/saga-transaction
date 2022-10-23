package org.sagaz.payment.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.payment.application.PaymentCreationService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class PurchaseConfirmedEventSubscriber {
    private final PaymentCreationService paymentCreationService;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "PurchaseConfirmedEvent.payment")
    @Transactional
    public void subscribe(final PurchaseConfirmedEvent event) {
        try {
            paymentCreationService.create(event.purchaseUuid, event.errorMode);
            if (event.errorMode == ErrorMode.PAYMENT_ERROR) throw new Error("Manually created Error");

        } catch (Error error) {
            PurchaseConfirmedSubscribingFailedEvent failedEvent =
                    new PurchaseConfirmedSubscribingFailedEvent(event.purchaseUuid);
            rabbitTemplate.convertAndSend(
                    "PurchaseConfirmedSubscribingFailedEvent",
                    "#", failedEvent
            );
            throw new AmqpRejectAndDontRequeueException(error);
        }
    }
}
