package org.sagaz.purchase.domain;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PurchaseConfirmedEvent {
    public UUID purchaseUuid;
    public Boolean errorMode = false;
}
