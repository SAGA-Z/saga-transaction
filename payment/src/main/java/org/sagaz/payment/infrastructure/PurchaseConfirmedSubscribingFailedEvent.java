package org.sagaz.payment.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PurchaseConfirmedSubscribingFailedEvent {
    public UUID purchaseUuid;
}
