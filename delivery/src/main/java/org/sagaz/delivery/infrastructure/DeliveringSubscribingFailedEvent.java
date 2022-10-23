package org.sagaz.delivery.infrastructure;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeliveringSubscribingFailedEvent {
    public UUID deliveryUuid;
}
