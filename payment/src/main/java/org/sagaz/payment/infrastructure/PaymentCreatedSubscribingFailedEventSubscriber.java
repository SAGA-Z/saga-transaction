package org.sagaz.payment.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.payment.domain.Payment;
import org.sagaz.payment.domain.PaymentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

//@Component
@RequiredArgsConstructor
public class PaymentCreatedSubscribingFailedEventSubscriber {
    private final PaymentRepository repository;
    private final RabbitTemplate rabbitTemplate;

    //@RabbitListener(queues = "PaymentCreatedSubscribingFailedEvent.payment")
    @Transactional
    public void subscribe(final PaymentCreatedSubscribingFailedEvent event) {
        System.out.println("Event Subscribing Failed");
        Payment payment = repository.findByUuid(event.paymentUuid).get();

        repository.delete(payment);

        PurchaseConfirmedSubscribingFailedEvent failedEvent =
                new PurchaseConfirmedSubscribingFailedEvent(payment.getPurchaseUuid());
        rabbitTemplate.convertAndSend(
                "PurchaseConfirmedSubscribingFailedEvent",
                "#", failedEvent
        );
    }
}
