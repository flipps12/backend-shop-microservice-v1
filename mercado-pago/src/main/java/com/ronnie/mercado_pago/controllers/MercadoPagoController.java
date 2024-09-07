package com.ronnie.mercado_pago.controllers;

import com.mercadopago.resources.preference.Preference;
import com.ronnie.mercado_pago.models.dtos.PreferenceControllerRequest;
import com.ronnie.mercado_pago.services.MercadoPagoPreferenceService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Preference> createPreference(@RequestBody PreferenceControllerRequest preferenceControllerRequest) {

        return mercadoPagoPreferenceService.createPreference(preferenceControllerRequest);
    }
}
