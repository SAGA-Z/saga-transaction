package sagaz.orchestrator.dto;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PaymentRequestDTO {
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
