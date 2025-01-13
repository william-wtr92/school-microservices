package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("school_route", r -> r.path("/api/school/**")
                        .uri("lb://school-service"))
                .route("student_route", r -> r.path("/api/student/**")
                        .uri("lb://student-service"))
                .build();
    }
}
