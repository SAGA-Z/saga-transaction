package org.sagaz.payment.application;

import lombok.RequiredArgsConstructor;
import org.sagaz.payment.domain.Payment;
import org.sagaz.payment.domain.PaymentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentDeletionService {
    private final PaymentRepository repository;

    @Transactional
    public void deleteByPurchaseUuid(UUID purchaseUuid) {
        Payment payment = repository.findByPurchaseUuid(purchaseUuid).get();
        repository.delete(payment);
    }
}
