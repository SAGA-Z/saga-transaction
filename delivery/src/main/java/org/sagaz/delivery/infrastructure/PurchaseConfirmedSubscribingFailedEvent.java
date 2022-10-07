package org.sagaz.delivery.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PurchaseConfirmedSubscribingFailedEvent {
    public UUID purchaseUuid;
}
