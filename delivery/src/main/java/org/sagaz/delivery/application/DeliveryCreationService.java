package org.sagaz.delivery.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.sagaz.delivery.domain.Delivery;
import org.sagaz.delivery.domain.DeliveryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryCreationService {
    private final DeliveryRepository repository;

    @Transactional
    public Delivery create(UUID purchaseUuid) {
        Delivery delivery = new Delivery(purchaseUuid, createRandomMessengerId());

        return repository.save(delivery);
    }

    private String createRandomMessengerId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}