package br.com.attus.contratomanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
                        url = "https://attus-contrato-manager-f4b71e29a75f.herokuapp.com"
                ),
                @Server(
                        description = "Teste",
                        url = "http://localhost:8080"
                )
        }
)
public class SwaggerConfig implements WebMvcConfigurer {
}