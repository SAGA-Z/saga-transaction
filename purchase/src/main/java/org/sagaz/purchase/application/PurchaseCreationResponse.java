package org.sagaz.purchase.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PurchaseCreationResponse {
    public UUID uuid;
    public String name;
}
