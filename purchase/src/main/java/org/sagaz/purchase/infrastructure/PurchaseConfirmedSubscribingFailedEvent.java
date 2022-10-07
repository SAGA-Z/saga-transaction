package org.sagaz.purchase.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class PurchaseConfirmedSubscribingFailedEvent {
    public UUID purchaseUuid;
}
