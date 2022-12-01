package sagaz.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrchestratorResponseDTO {
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
