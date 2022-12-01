package org.sagaz.delivery.application;

import lombok.RequiredArgsConstructor;
import org.sagaz.delivery.domain.Delivery;
import org.sagaz.delivery.domain.DeliveryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DeliveryDeletionService {
    private final DeliveryRepository repository;

    @Transactional
    public void deleteByPurchaseUuid(UUID purchaseUuid) {
        Delivery delivery = repository.findByPurchaseUuid(purchaseUuid).get();
        repository.delete(delivery);
    }
}