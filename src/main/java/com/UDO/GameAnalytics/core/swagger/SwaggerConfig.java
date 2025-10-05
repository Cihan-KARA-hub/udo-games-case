package com.UDO.GameAnalytics.core.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    private final WebEndpointsSupplier webEndpointsSupplier;

    public SwaggerConfig(WebEndpointsSupplier webEndpointsSupplier) {
        this.webEndpointsSupplier = webEndpointsSupplier;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info().title("GameAnalytics API").version("1.0"));

        Paths paths = new Paths();

        // Tüm actuator endpointlerini ekle
        webEndpointsSupplier.getEndpoints().forEach(endpoint -> {
            String rootPath = "/actuator/" + endpoint.getRootPath();
            paths.addPathItem(rootPath, new PathItem());
        });

        openAPI.setPaths(paths);
        return openAPI;
    }
}
