package sagaz.orchestrator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import sagaz.orchestrator.dto.OrchestratorRequestDTO;
import sagaz.orchestrator.dto.OrchestratorResponseDTO;
import sagaz.orchestrator.service.OrchestratorService;

import java.util.function.Function;

@Configuration
public class OrchestratorConfig {

    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> processor(){
        return flux -> flux
                            .flatMap(dto -> this.orchestratorService.confirmOrder(dto))
                            .doOnNext(dto -> System.out.println("Error Mode : " + dto.errorMode));
    }

}
