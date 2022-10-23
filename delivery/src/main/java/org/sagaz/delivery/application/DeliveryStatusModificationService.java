package org.sagaz.delivery.application;

import lombok.RequiredArgsConstructor;
import org.sagaz.delivery.domain.Delivery;
import org.sagaz.delivery.domain.DeliveryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryStatusModificationService {
    private final DeliveryRepository repository;

    @Transactional
    public void deliver(UUID uuid) throws DeliveryNotFoundException {
        Delivery delivery = repository.findByUuid(uuid).orElseThrow(new DeliveryNotFoundException());

        delivery.deliver();

        repository.save(delivery);
    }

    @Transactional
    public void delivered(UUID uuid) throws DeliveryNotFoundException {
        Delivery delivery = repository.findByUuid(uuid).orElseThrow(new DeliveryNotFoundException());

        delivery.delivered();

        repository.save(delivery);
    }
}
