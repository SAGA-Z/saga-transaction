package org.sagaz.payment.application;

import lombok.RequiredArgsConstructor;
import org.sagaz.payment.domain.Payment;
import org.sagaz.payment.domain.PaymentRepository;
import org.sagaz.payment.infrastructure.ErrorMode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentCreationService {
    private final PaymentRepository repository;

    @Transactional
    public Payment create(UUID purchaseUuid, ErrorMode errorMode) {
        Payment payment = new Payment(purchaseUuid, (long)(Math.random() * 10000));

        payment.publishPaymentCreatedEvent(errorMode);
        return repository.save(payment);
    }
}
