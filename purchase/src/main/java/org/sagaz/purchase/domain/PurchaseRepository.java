package org.sagaz.purchase.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findByUuid(UUID uuid);
}
