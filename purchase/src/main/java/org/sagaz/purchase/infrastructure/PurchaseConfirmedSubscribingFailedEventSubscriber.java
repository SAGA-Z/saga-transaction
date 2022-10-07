package org.sagaz.purchase.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.domain.Purchase;
import org.sagaz.purchase.domain.PurchaseRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class PurchaseConfirmedSubscribingFailedEventSubscriber {
    private final PurchaseRepository repository;

    @RabbitListener(queues = "PurchaseConfirmedSubscribingFailedEvent.purchase")
    @Transactional
    public void subscribe(final PurchaseConfirmedSubscribingFailedEvent event) {
        System.out.println("Event Subscribing Failed");
        Purchase purchase = repository.findByUuid(event.purchaseUuid).get();

        purchase.unConfirm();
    }
}
