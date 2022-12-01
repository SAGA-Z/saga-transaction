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

        // 여기서는 왜 save를 호출하지 않을까? 다른 코드에서는 필드 변경시 save를 명시적으로 호출해주었는데...
    }
}
