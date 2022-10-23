package org.sagaz.payment.infrastructure;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentCreatedSubscribingFailedEvent {
    public UUID paymentUuid;
}
