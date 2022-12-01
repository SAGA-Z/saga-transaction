package org.sagaz.payment.presentation;

import lombok.RequiredArgsConstructor;
import org.sagaz.payment.application.PaymentCreationService;
import org.sagaz.payment.application.PaymentDeletionService;
import org.sagaz.payment.domain.Payment;
import org.sagaz.payment.domain.PaymentCreatedEvent;
import org.sagaz.payment.infrastructure.PaymentRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentCreationService paymentCreationService;
    private final PaymentDeletionService paymentDeletionService;


    @PostMapping("/create")
    public PaymentCreatedEvent create(@RequestBody PaymentRequestDTO requestDTO) {
        System.out.println("PaymentController.create");
        Payment payment = paymentCreationService.create(requestDTO.purchaseUuid, requestDTO.errorMode);
        return new PaymentCreatedEvent(payment.getUuid(), requestDTO.purchaseUuid, requestDTO.errorMode);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody PaymentRequestDTO requestDTO) {
        System.out.println("PaymentController.delete");
        paymentDeletionService.deleteByPurchaseUuid(requestDTO.purchaseUuid);
    }


}
