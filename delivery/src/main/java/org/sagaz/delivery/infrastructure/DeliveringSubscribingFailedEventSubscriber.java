package org.sagaz.delivery.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sagaz.delivery.domain.Delivery;
import org.sagaz.delivery.domain.DeliveryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class DeliveringSubscribingFailedEventSubscriber {
    private final DeliveryRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "DeliveringSubscribingFailedEvent.delivery")
    @Transactional
    public void subscribe(final DeliveringSubscribingFailedEvent event) {
        System.out.println("Event Subscribing Failed");
        Delivery delivery = repository.findByUuid(event.deliveryUuid).get();

        delivery.prepare();
    }
}
