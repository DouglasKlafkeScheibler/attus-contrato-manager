package br.com.attus.contratomanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Gerenciamento de Contratos Jurídicos",
        version = "1.0.0",
        description = "Documentação da API de Gerenciamento de Contratos Jurídicos"
    ),
        servers = {
            @Server(
                    description = "Ambiente Heroku",
                    url = "http://Teste:8080"
            ),
            @Server(
                    description = "Ambiente Local",
                    url = "http://localhost:8080"
            )
        }
)
public class SwaggerConfig {
}