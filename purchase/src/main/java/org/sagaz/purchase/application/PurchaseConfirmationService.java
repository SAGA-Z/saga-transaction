package org.sagaz.purchase.application;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.domain.Purchase;
import org.sagaz.purchase.domain.PurchaseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseConfirmationService {
    private final PurchaseRepository repository;

    @Transactional
    public void confirm(UUID uuid) throws PurchaseNotFoundException {
        Purchase purchase = repository.findByUuid(uuid).orElseThrow(new PurchaseNotFoundException());

        purchase.confirm();
    }
}
