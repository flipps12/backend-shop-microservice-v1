package com.ronnie.mercado_pago.controllers;

import com.ronnie.mercado_pago.services.MercadoPagoPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/webhook/{seller}")
@RequiredArgsConstructor
public class WebHooksController {
    private final MercadoPagoPreferenceService mercadoPagoPreferenceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> handleWebhook(@PathVariable("seller") String seller, @RequestBody Map<String, Object> payload, @RequestHeader HttpHeaders headers) throws Exception {

        String topic = (String) payload.get("type");

        // agregar verificado de token

        if ("payment".equals(topic)) {
            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            if (data != null) {
                String dataId = (String) data.get("id");
                if (dataId != null) {
                    return mercadoPagoPreferenceService.processPayment(dataId, seller);
                } else {
                    return ResponseEntity.status(400).body("Missing data.id");
                }
            } else {
                return ResponseEntity.status(400).body("Missing data object");
            }
        }

        return ResponseEntity.ok("Received");
    }
}
