package org.sagaz.delivery.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByUuid(UUID uuid);
    Optional<Delivery> findByPaymentUuid(UUID uuid);
}
