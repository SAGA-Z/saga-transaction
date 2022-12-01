package sagaz.orchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sagaz.orchestrator.dto.*;
import sagaz.orchestrator.service.steps.DeliveryStep;
import sagaz.orchestrator.service.steps.PaymentStep;

import java.util.List;

@Service
public class OrchestratorService {

    @Autowired
    @Qualifier("payment")
    private WebClient paymentClient;

    @Autowired
    @Qualifier("delivery")
    private WebClient deliveryClient;

    public Mono<OrchestratorResponseDTO> confirmOrder(final OrchestratorRequestDTO requestDTO){
        Workflow orderWorkflow = this.getOrderWorkflow(requestDTO);
        return Flux.fromStream(() -> orderWorkflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .handle(((aBoolean, synchronousSink) -> {
                    if(aBoolean)
                        synchronousSink.error(new WorkflowException("confirm order failed!"));
                    else
                        synchronousSink.next(true);
                }))
                .then(Mono.fromCallable(() -> getResponseDTO(requestDTO, ErrorMode.NO_ERROR)))
                .onErrorResume(ex -> this.revertOrder(orderWorkflow, requestDTO));

    }

    public OrchestratorResponseDTO confirmOrderBlocked(final OrchestratorRequestDTO requestDTO){
        WorkflowStep paymentStep = new PaymentStep(this.paymentClient, this.getPaymentRequestDTO(requestDTO));
        WorkflowStep deliveryStep = new DeliveryStep(this.deliveryClient, this.getDeliveryRequestDTO(requestDTO));

        PaymentCreatedResponseDto paymentCreatedResponseDto = (PaymentCreatedResponseDto) paymentStep.processBlocked();
        if (paymentCreatedResponseDto.errorMode==ErrorMode.PAYMENT_ERROR){
            paymentStep.revert().subscribe();
            return getResponseDTO(requestDTO, requestDTO.errorMode);
        }
        DeliveryCreatedResponseDto deliveryCreatedResponseDto = (DeliveryCreatedResponseDto) deliveryStep.processBlocked();
        if (deliveryCreatedResponseDto.errorMode==ErrorMode.DELIVERY_ERROR){
            paymentStep.revert().subscribe();
            deliveryStep.revert().subscribe();
            return getResponseDTO(requestDTO, requestDTO.errorMode);
        }
        return getResponseDTO(requestDTO, ErrorMode.NO_ERROR);
    }


    private Mono<OrchestratorResponseDTO> revertOrder(final Workflow workflow, final OrchestratorRequestDTO requestDTO){
        return Flux.fromStream(() -> workflow.getSteps().stream())
                //.filter(wf -> !wf.getStatus().equals(WorkflowStepStatus.PENDING))
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.just(this.getResponseDTO(requestDTO, requestDTO.errorMode)));
    }

    private Workflow getOrderWorkflow(OrchestratorRequestDTO requestDTO){
        WorkflowStep paymentStep = new PaymentStep(this.paymentClient, this.getPaymentRequestDTO(requestDTO));
        WorkflowStep deliveryStep = new DeliveryStep(this.deliveryClient, this.getDeliveryRequestDTO(requestDTO));
        return new OrderWorkflow(List.of(paymentStep, deliveryStep));
    }

    private OrchestratorResponseDTO getResponseDTO(OrchestratorRequestDTO requestDTO, ErrorMode errorMode){
        OrchestratorResponseDTO responseDTO = new OrchestratorResponseDTO(requestDTO.purchaseUuid, errorMode);
        return responseDTO;
    }

    private PaymentRequestDTO getPaymentRequestDTO(OrchestratorRequestDTO requestDTO){
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(requestDTO.purchaseUuid, requestDTO.errorMode);
        System.out.println("paymentRequestDTO.purchaseUuid = " + paymentRequestDTO.purchaseUuid);
        System.out.println("paymentRequestDTO.errorMode = " + paymentRequestDTO.errorMode);
        return paymentRequestDTO;
    }

    private DeliveryRequestDto getDeliveryRequestDTO(OrchestratorRequestDTO requestDTO){
        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto(requestDTO.purchaseUuid, requestDTO.errorMode);
        return deliveryRequestDto;
    }
}
