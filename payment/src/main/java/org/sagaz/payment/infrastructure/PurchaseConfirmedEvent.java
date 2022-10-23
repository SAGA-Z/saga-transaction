package org.sagaz.payment.infrastructure;

import lombok.Data;

import java.util.UUID;

@Data
public class PurchaseConfirmedEvent {
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
