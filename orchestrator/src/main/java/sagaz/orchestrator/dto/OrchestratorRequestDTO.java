package sagaz.orchestrator.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrchestratorRequestDTO {
    public UUID purchaseUuid;
    public ErrorMode errorMode;
}
