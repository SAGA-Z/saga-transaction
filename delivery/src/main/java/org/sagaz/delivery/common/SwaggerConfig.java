package org.sagaz.delivery.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("delivery")
                .version("0.0.1")
                .description(("delivery open api docs"));

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
