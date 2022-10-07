package org.sagaz.delivery.infrastructure;

import lombok.Data;

import java.util.UUID;

@Data
public class PurchaseConfirmedEvent {
    public UUID purchaseUuid;
    public Boolean errorMode;
}
