package sagaz.orchestrator.service.steps;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sagaz.orchestrator.dto.DeliveryCreatedResponseDto;
import sagaz.orchestrator.dto.ErrorMode;
import sagaz.orchestrator.dto.PaymentCreatedResponseDto;
import sagaz.orchestrator.dto.PaymentRequestDTO;
import sagaz.orchestrator.service.WorkflowStep;
import sagaz.orchestrator.service.WorkflowStepStatus;

public class PaymentStep implements WorkflowStep {

    private final WebClient webClient;
    private final PaymentRequestDTO requestDTO;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public PaymentStep(WebClient webClient, PaymentRequestDTO requestDTO) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return stepStatus;
    }

    private Mono<PaymentCreatedResponseDto> requestCreate(){
        return webClient
                .post()
                .uri("/create")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(PaymentCreatedResponseDto.class);
    }

    @Override
    public Mono<Boolean> process() {
        System.out.println("PaymentStep.process");
        return requestCreate()
                .map(r -> r.errorMode.equals(ErrorMode.PAYMENT_ERROR))
                // true이면 failed, false이면 complete 발행.
                .doOnNext(b -> stepStatus = b ? WorkflowStepStatus.FAILED : WorkflowStepStatus.COMPLETE);

    }

    @Override
    public Object processBlocked() {
        return requestCreate().block();
    }


    @Override
    public Mono<Boolean> revert() {
        System.out.println("PaymentStep.revert");
        return this.webClient
                .post()
                .uri("/delete")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }

}
