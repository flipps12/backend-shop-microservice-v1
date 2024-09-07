package com.ronnie.mercado_pago.services;

import com.mercadopago.client.preference.*;
import com.mercadopago.net.HttpStatus;
import com.ronnie.mercado_pago.models.dtos.MercadoPagoPreferenceItemsRequest;
import com.ronnie.mercado_pago.models.dtos.MercadoPagoPreferenceRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import com.mercadopago.resources.preference.Preference;
import com.ronnie.mercado_pago.models.dtos.PreferenceControllerRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MercadoPagoPreferenceService {


    public String createPreference(MercadoPagoPreferenceRequest mercadoPagoPreferenceRequest) {

        PreferenceClient client = new PreferenceClient();
        List<PreferenceItemRequest> itemsRequest = new ArrayList();

//        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
//                .title()
//                .quantity()
//                .unitPrice(BigDecimal.valueOf())
//                .description()
//                .build();

        PreferencePayerRequest payerRequest = PreferencePayerRequest.builder()
                .email("")
                .build();

        for (MercadoPagoPreferenceItemsRequest product : mercadoPagoPreferenceRequest.getItems()) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(product.getTitle())
                    .quantity(product.getQuantity().intValue())
                    .unitPrice(BigDecimal.valueOf(product.getPrice()))
                    .description(product.getDescription())
                    .build();

            itemsRequest.add(itemRequest);
        }

        PreferenceRequest request = PreferenceRequest.builder()
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("")
                        .pending("")
                        .failure("")
                        .build())
                .items(itemsRequest)
                .payer(payerRequest)
                .externalReference(mercadoPagoPreferenceRequest.getExternalReference())
                .build();

        try {
            Preference preference = client.create(request);
            return preference.getInitPoint();
        } catch (Exception e) {
            return null;
        }
    }
}
