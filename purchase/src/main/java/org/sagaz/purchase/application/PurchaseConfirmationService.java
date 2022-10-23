package org.sagaz.purchase.application;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.domain.ErrorMode;
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

        purchase.confirm(ErrorMode.NO_ERROR);

        repository.save(purchase);
    }

    @Transactional
    public void confirm(UUID uuid, ErrorMode errorMode) throws PurchaseNotFoundException {
        Purchase purchase = repository.findByUuid(uuid).orElseThrow(new PurchaseNotFoundException());

        purchase.confirm(errorMode);

        repository.save(purchase);
    }
}
