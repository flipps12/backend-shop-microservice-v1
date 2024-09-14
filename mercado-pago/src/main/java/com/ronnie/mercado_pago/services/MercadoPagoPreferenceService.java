package com.ronnie.mercado_pago.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.payment.Payment;
import com.ronnie.mercado_pago.models.dtos.MercadoPagoPreferenceItemsRequest;
import com.ronnie.mercado_pago.models.dtos.MercadoPagoPreferenceRequest;
import com.ronnie.mercado_pago.models.entities.MercadoPagoSellers;
import com.ronnie.mercado_pago.repositories.MercadoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MercadoPagoPreferenceService {

    private final MercadoPagoRepository mercadoPagoRepository;
    private final WebClient.Builder webClientBuilder;
    private final String urlNotification = "https://2609-2800-810-48e-2b8-d585-f9d7-1bff-3162.ngrok-free.app";

    public String createPreference(MercadoPagoPreferenceRequest mercadoPagoPreferenceRequest) {
        Optional<MercadoPagoSellers> secretToken = mercadoPagoRepository.findBySeller(mercadoPagoPreferenceRequest.getSeller());
        if (secretToken.isEmpty()) return "seller unknown";

        MercadoPagoConfig.setAccessToken(secretToken.get().getToken());

        PreferenceClient client = new PreferenceClient();
        List<PreferenceItemRequest> itemsRequest = new ArrayList();

        PreferencePayerRequest payerRequest = PreferencePayerRequest.builder()
                .email("")
                .build();

        for (MercadoPagoPreferenceItemsRequest product : mercadoPagoPreferenceRequest.getItems()) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(product.getId())
                    .title(product.getTitle())
                    .quantity(product.getQuantity().intValue())
                    .unitPrice(BigDecimal.valueOf(product.getPrice()))
                    .description(product.getDescription())
                    .currencyId("ARS")
                    .pictureUrl(product.getPictureUrl())
                    .build();

            itemsRequest.add(itemRequest);
        }

        PreferenceRequest request = PreferenceRequest.builder()
                .notificationUrl(urlNotification + "/webhook/" + mercadoPagoPreferenceRequest.getSeller())
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("/success")
                        .pending("/pending")
                        .failure("/failure")
                        .build())
                .items(itemsRequest)
                .payer(payerRequest)
                .externalReference(mercadoPagoPreferenceRequest.getExternalReference())
                .build();

        try {
            Preference preference = client.create(request);
            return preference.getInitPoint();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ResponseEntity<String> processPayment(String dataId, String seller) {
        
        String url = "https://api.mercadopago.com/v1/payments/" + dataId + "?access_token=" +  mercadoPagoRepository.findBySeller(seller).get().getToken();


        try {
            String paymentDetails = webClientBuilder.build() // peticion checkear stock
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            // Procesa los detalles del pago
            System.out.println("Payment details: " + paymentDetails);

            // Aquí puedes agregar lógica adicional para validar y procesar el pago

            String setStatus = webClientBuilder.build() // mejorar peticion, etc
                    .post()
                    .uri("http://localhost:8080/api/order/setStatus")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(paymentDetails)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println(setStatus);

            return ResponseEntity.ok("Payment processed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing payment");
        }

    }
}
