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
                        .filters(f -> f.circuitBreaker(cb -> cb
                                .setName("schoolServiceCB")
                                .setFallbackUri("forward:/fallback/school")))
                        .uri("lb://school-service"))
                .route("student_route", r -> r.path("/api/student/**")
                        .filters(f -> f.circuitBreaker(cb -> cb
                                .setName("studentServiceCB")
                                .setFallbackUri("forward:/fallback/student")))
                        .uri("lb://student-service"))
                .build();
    }
}
