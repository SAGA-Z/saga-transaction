package sagaz.orchestrator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import sagaz.orchestrator.dto.ErrorMode;
import sagaz.orchestrator.dto.PaymentCreatedResponseDto;
import sagaz.orchestrator.dto.PaymentRequestDTO;

import java.util.UUID;

@RestController
public class TestController {
    private WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8003")
            .build();

    @GetMapping
    public PaymentCreatedResponseDto test() {
        PaymentRequestDTO dto = new PaymentRequestDTO(UUID.randomUUID(), ErrorMode.NO_ERROR);
        return webClient.post()         // POST method
                .uri("/create")    // baseUrl 이후 uri
                .bodyValue(dto)     // set body value
                .retrieve()                 // client message 전송
                .bodyToMono(PaymentCreatedResponseDto.class)  // body type : EmpInfo
                .block();
    }

    @PostMapping
    public String post(){
        return "post";
    }
}