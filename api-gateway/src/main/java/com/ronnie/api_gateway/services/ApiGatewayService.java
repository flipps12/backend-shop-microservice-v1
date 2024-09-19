package com.ronnie.api_gateway.services;

import com.ronnie.api_gateway.entities.models.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ApiGatewayService {

    private final WebClient.Builder webClientBuilder;

    public Mono<StatusResponse> getAllStatus() {
        return Mono.zip(
                Mono.just("online"),
                getStatus("auth", "8084"),
                getStatus("email", "8085"),
                getStatus("mercado-pago", "8083"),
                getStatus("order", "8081"),
                getStatus("product", "8082")
        ).map(tuple -> StatusResponse.builder()
                .gateway(tuple.getT1())
                .auth(tuple.getT2())
                .notification(tuple.getT3())
                .mercado_pago(tuple.getT4())
                .order(tuple.getT5())
                .product(tuple.getT6())
                .build());
    }

    public Mono<String> getStatus(String api, String port) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:" + port + "/api/" + api + "/status")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("offline"));
    }

}
