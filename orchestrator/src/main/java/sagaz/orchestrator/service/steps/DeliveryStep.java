package sagaz.orchestrator.service.steps;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sagaz.orchestrator.dto.DeliveryCreatedResponseDto;
import sagaz.orchestrator.dto.DeliveryRequestDto;
import sagaz.orchestrator.dto.ErrorMode;
import sagaz.orchestrator.service.WorkflowStep;
import sagaz.orchestrator.service.WorkflowStepStatus;

public class DeliveryStep implements WorkflowStep {

    private final WebClient webClient;
    private final DeliveryRequestDto requestDTO;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public DeliveryStep(WebClient webClient, DeliveryRequestDto requestDTO) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return stepStatus;
    }

    private Mono<DeliveryCreatedResponseDto> requestCreate(){
        return webClient
                .post()
                .uri("/create")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(DeliveryCreatedResponseDto.class);
    }

    @Override
    public Mono<Boolean> process() {
        System.out.println("DeliveryStep.process");
        return requestCreate()
                .map(r -> r.errorMode.equals(ErrorMode.DELIVERY_ERROR))
                .doOnNext(b -> stepStatus = b ? WorkflowStepStatus.FAILED : WorkflowStepStatus.COMPLETE );
    }

    @Override
    public Object processBlocked() {
        return requestCreate().block();
    }

    @Override
    public Mono<Boolean> revert() {
        System.out.println("DeliveryStep.revert");
        return webClient
                    .post()
                    .uri("/delete")
                    .body(BodyInserters.fromValue(requestDTO))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .map(r ->true)
                    .onErrorReturn(false);
    }

}
