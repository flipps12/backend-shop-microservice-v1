package com.ronnie.mercado_pago.controllers;

import com.mercadopago.resources.preference.Preference;
import com.ronnie.mercado_pago.models.dtos.MercadoPagoPreferenceRequest;
import com.ronnie.mercado_pago.models.dtos.PreferenceControllerRequest;
import com.ronnie.mercado_pago.services.MercadoPagoPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercado-pago")
@RequiredArgsConstructor
public class MercadoPagoController {

    private final MercadoPagoPreferenceService mercadoPagoPreferenceService;

    @PostMapping("create-preference")
    @ResponseStatus(HttpStatus.CREATED)
    public String createPreference(@RequestBody MercadoPagoPreferenceRequest mercadoPagoPreferenceRequest) {

        // Agregar peticion get al resource payment
        // y devolver true al status del order

        System.out.println(mercadoPagoPreferenceRequest);
        return mercadoPagoPreferenceService.createPreference(mercadoPagoPreferenceRequest);
    }
}
