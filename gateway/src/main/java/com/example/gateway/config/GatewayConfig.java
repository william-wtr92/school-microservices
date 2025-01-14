package com.example.gateway.config;

import com.example.gateway.security.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_route", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service"))
                .route("school_route", r -> r.path("/api/school/**")
                        .filters(f -> f.filter(authenticationFilter)
                                .circuitBreaker(cb -> cb
                                        .setName("schoolServiceCB")
                                        .setFallbackUri("forward:/fallback/school")))
                        .uri("lb://school-service"))
                .route("student_route", r -> r.path("/api/student/**")
                        .filters(f -> f.filter(authenticationFilter)
                                .circuitBreaker(cb -> cb
                                        .setName("studentServiceCB")
                                        .setFallbackUri("forward:/fallback/student")))
                        .uri("lb://student-service"))
                .build();
    }
}
