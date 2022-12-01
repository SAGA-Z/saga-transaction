package org.sagaz.delivery.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DeliveryCreatedEvent {
    public UUID deliveryUuid;
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
