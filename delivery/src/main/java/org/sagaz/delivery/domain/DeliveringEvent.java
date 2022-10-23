package org.sagaz.delivery.domain;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeliveringEvent {
    public UUID deliveryUuid;
    public String receiverMessengerId;
}
