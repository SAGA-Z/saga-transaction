package sagaz.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentCreatedResponseDto {
    public UUID paymentUuid;
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
