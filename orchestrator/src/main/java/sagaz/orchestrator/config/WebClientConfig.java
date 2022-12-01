package sagaz.orchestrator.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("payment")
    public WebClient paymentClient(@Value("${service.endpoints.payment}") String endpoint){
        return WebClient.builder()
                .baseUrl("http://localhost:8003")
                .build();
    }

    @Bean
    @Qualifier("delivery")
    public WebClient deliveryClient(@Value("${service.endpoints.delivery}") String endpoint){
        return WebClient.builder()
                .baseUrl("http://localhost:8002")
                .build();
    }

}
