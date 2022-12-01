package sagaz.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryCreatedResponseDto {
    public UUID deliveryUuid;
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
