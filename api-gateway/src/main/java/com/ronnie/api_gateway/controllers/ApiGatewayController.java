package com.ronnie.api_gateway.controllers;

import com.ronnie.api_gateway.entities.models.StatusResponse;
import com.ronnie.api_gateway.services.ApiGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ApiGatewayController {

    private final WebClient.Builder webClientBuilder;
    private final ApiGatewayService apiGatewayService;

    @GetMapping("/status")
    public String getStatus() {
        return "online"; // añadir online de los demas servers
    }

    @GetMapping()
    public Mono<StatusResponse> getAllStatus() {
        return apiGatewayService.getAllStatus();
    }
}
