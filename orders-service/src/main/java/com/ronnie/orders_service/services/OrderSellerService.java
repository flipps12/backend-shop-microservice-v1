package com.ronnie.orders_service.services;

import com.ronnie.orders_service.models.dtos.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderSellerService {

    private final WebClient.Builder webClientBuilder;

    public Boolean authCookie(String cookie) {
        String result =  this.webClientBuilder.build() // pedir role de la cookie
                .get()
                .uri("https://localhost:8080/api/auth/extract/" + cookie)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (result == null) return false;
        if (result.equals("seller") || result.equals("admin")) return true; // cambiar solo admin o admin y sellers
        return false;
    }
}
