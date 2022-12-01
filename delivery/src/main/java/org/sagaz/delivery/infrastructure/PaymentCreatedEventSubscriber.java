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
public class PaymentCreatedEventSubscriber {
    private final DeliveryCreationService deliveryCreationService;
    private final RabbitTemplate rabbitTemplate;

    //@RabbitListener(queues = "PaymentCreatedEvent.delivery")
    @Transactional
    public void subscribe(final PaymentCreatedEvent event) {
        try {
            deliveryCreationService.create(event.purchaseUuid);
            if (event.errorMode == ErrorMode.DELIVERY_ERROR) throw new Error("Manually created Error");

        } catch (Error error) {
            PaymentCreatedSubscribingFailedEvent failedEvent =
                    new PaymentCreatedSubscribingFailedEvent(event.paymentUuid);
            rabbitTemplate.convertAndSend(
                    "PaymentCreatedSubscribingFailedEvent",
                    "#", failedEvent
            );
            throw new AmqpRejectAndDontRequeueException(error);
        }
    }
}
