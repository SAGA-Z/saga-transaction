package sagaz.orchestrator.dto;

import lombok.AllArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
public class DeliveryRequestDto {
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
