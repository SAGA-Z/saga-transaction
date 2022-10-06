package org.sagaz.purchase.application;

import lombok.RequiredArgsConstructor;
import org.sagaz.purchase.domain.Purchase;
import org.sagaz.purchase.domain.PurchaseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseCreationService {
    private final PurchaseRepository repository;

    @Transactional
    public PurchaseCreationResponse create(PurchaseCreationRequest request) {
        Purchase purchase = new Purchase(request.name);

        repository.save(purchase);

        return new PurchaseCreationResponse(purchase.getUuid(), purchase.getName());
    }
}