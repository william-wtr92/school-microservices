package com.example.gateway.security;

import com.example.gateway.utils.AuthServiceEndpoints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {
    private final WebClient webClient;
    private final String authServiceUrl;

    public AuthenticationFilter(WebClient webClient, @Value("${auth-service.url}") String authServiceUrl) {
        this.webClient = webClient;
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String token = request.getCookies().getFirst("jwt") != null
                ? request.getCookies().getFirst("jwt").getValue()
                : null;

        if (token == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return webClient.get()
                .uri(authServiceUrl + AuthServiceEndpoints.VALIDATE)
                .cookie("jwt", token)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                        clientResponse -> clientResponse.createException().flatMap(Mono::error))
                .bodyToMono(String.class)
                .flatMap(validationResponse -> {
                    return chain.filter(exchange);
                })
                .onErrorResume(e -> {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                });

    }
}


