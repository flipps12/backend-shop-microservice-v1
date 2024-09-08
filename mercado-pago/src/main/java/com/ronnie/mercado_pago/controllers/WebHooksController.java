package com.ronnie.mercado_pago.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebHooksController {
    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        // Procesa la notificación recibida
        System.out.println("Received webhook: " + payload);

        // Devuelve una respuesta 200 OK a Mercado Pago
        return ResponseEntity.ok("Received");
    }
}
