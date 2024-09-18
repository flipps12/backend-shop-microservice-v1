package com.ronnie.api_gateway.services;

import com.ronnie.api_gateway.entities.models.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ApiGatewayService {

    private final WebClient.Builder webClientBuilder;

    public StatusResponse getAllStatus() {
        return StatusResponse.builder()
                .gateway("online")
                .auth(getStatus("auth"))
                .notification(getStatus("email"))
                .mercado_pago(getStatus("mercado-pago"))
                .order(getStatus("order"))
                .product(getStatus("product"))
                .build();
    }

    public String getStatus(String api) {
        return webClientBuilder.build() // mejorar peticion, etc
                .get()
                .uri("http://localhost:8080/api/" + api + "/status")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
