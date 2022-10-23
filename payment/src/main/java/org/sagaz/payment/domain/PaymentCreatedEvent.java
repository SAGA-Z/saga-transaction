package org.sagaz.payment.domain;

import lombok.AllArgsConstructor;
import org.sagaz.payment.infrastructure.ErrorMode;

import java.util.UUID;

@AllArgsConstructor
public class PaymentCreatedEvent {
    public UUID paymentUuid;
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
