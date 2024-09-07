package com.ronnie.mercado_pago.services;

import com.mercadopago.client.preference.*;
import com.mercadopago.net.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import com.mercadopago.resources.preference.Preference;
import com.ronnie.mercado_pago.models.dtos.PreferenceControllerRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class MercadoPagoPreferenceService {


    public ResponseEntity<Preference> createPreference(PreferenceControllerRequest preferenceControllerRequest) {

        PreferenceClient client = new PreferenceClient();

        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title(preferenceControllerRequest.getTitle())
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(preferenceControllerRequest.getPrice()))
                .description(preferenceControllerRequest.getDescription())
                .build();

        PreferencePayerRequest payerRequest = PreferencePayerRequest.builder()
                .email(preferenceControllerRequest.getEmail())
                .build();

        PreferenceRequest request = PreferenceRequest.builder()
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("")
                        .pending("")
                        .failure("")
                        .build())
                .items(Collections.singletonList(itemRequest))
                .payer(payerRequest)
                .externalReference(preferenceControllerRequest.getExternalReference())
                .build();

        try {
            Preference preference = client.create(request);
            return ResponseEntity.ok(preference);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
