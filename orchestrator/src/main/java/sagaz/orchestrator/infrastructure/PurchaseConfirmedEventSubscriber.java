package sagaz.orchestrator.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import sagaz.orchestrator.dto.ErrorMode;
import sagaz.orchestrator.dto.OrchestratorRequestDTO;
import sagaz.orchestrator.dto.OrchestratorResponseDTO;
import sagaz.orchestrator.dto.PurchaseConfirmedSubscribingFailedEvent;
import sagaz.orchestrator.service.OrchestratorService;

@Component
@RequiredArgsConstructor
public class PurchaseConfirmedEventSubscriber {
    private final RabbitTemplate rabbitTemplate;
    private final OrchestratorService orchestratorService;

    @RabbitListener(queues = "PurchaseConfirmedEvent.payment")
    public void subscribe(final OrchestratorRequestDTO orchestratorRequestDTO) {
        try {
            OrchestratorResponseDTO dto = confirmOrder(orchestratorRequestDTO, false);
            System.out.println("dto = " + dto);
            if (dto.errorMode != ErrorMode.NO_ERROR) throw new Error("Manually created Error");

        } catch (Error error) {
            PurchaseConfirmedSubscribingFailedEvent failedEvent =
                    new PurchaseConfirmedSubscribingFailedEvent(orchestratorRequestDTO.getPurchaseUuid());
            rabbitTemplate.convertAndSend(
                    "PurchaseConfirmedSubscribingFailedEvent",
                    "#", failedEvent
            );
            throw new AmqpRejectAndDontRequeueException(error);
        }
    }

    private OrchestratorResponseDTO confirmOrder(OrchestratorRequestDTO orchestratorRequestDTO, boolean isBlocked){
        if (isBlocked){
            return orchestratorService.confirmOrderBlocked(orchestratorRequestDTO);
        }
        Mono<OrchestratorResponseDTO> orchestratorResponseDTOMono = orchestratorService.confirmOrder(orchestratorRequestDTO);
        return orchestratorResponseDTOMono.block();
    }
}
