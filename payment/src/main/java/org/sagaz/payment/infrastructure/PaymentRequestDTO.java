package org.sagaz.payment.infrastructure;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PaymentRequestDTO {
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}

