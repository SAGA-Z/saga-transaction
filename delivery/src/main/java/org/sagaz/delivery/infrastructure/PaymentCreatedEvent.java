package org.sagaz.delivery.infrastructure;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentCreatedEvent {
    public UUID paymentUuid;
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
