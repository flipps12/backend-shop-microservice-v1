package com.ronnie.mercado_pago.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.net.HttpStatus;
import com.ronnie.mercado_pago.models.dtos.MercadoPagoPreferenceItemsRequest;
import com.ronnie.mercado_pago.models.dtos.MercadoPagoPreferenceRequest;
import com.ronnie.mercado_pago.models.entities.MercadoPagoSellers;
import com.ronnie.mercado_pago.models.entities.UserIdPreference;
import com.ronnie.mercado_pago.repositories.MercadoPagoRepository;
import com.ronnie.mercado_pago.repositories.UserIdPreferenceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import com.mercadopago.resources.preference.Preference;
import com.ronnie.mercado_pago.models.dtos.PreferenceControllerRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MercadoPagoPreferenceService {

    private final MercadoPagoRepository mercadoPagoRepository;
    private final UserIdPreferenceRepository userIdPreferenceRepository;
    private final String urlNotification = "https://12d6-2800-810-48e-2b8-2c9e-2604-4675-ada6.ngrok-free.app";

    public String createPreference(MercadoPagoPreferenceRequest mercadoPagoPreferenceRequest) {
        String secretToken = mercadoPagoRepository.findBySeller(mercadoPagoPreferenceRequest.getSeller()).get().getToken();

        MercadoPagoConfig.setAccessToken(secretToken);

        PreferenceClient client = new PreferenceClient();
        List<PreferenceItemRequest> itemsRequest = new ArrayList();

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
                .notificationUrl(urlNotification + "/webhook")
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
            System.out.println("preference: " + preference.getCollectorId());
//            if (userIdPreferenceRepository.findByUserId(preference.getCollectorId()).stream().findFirst().get().getToken() == secretToken) {
//                userIdPreferenceRepository.save(UserIdPreference.builder()
//                    .token(secretToken)
//                    .userId(preference.getCollectorId())
//                    .build());
//            }

            userIdPreferenceRepository.save(UserIdPreference.builder()
                    .token(secretToken)
                    .userId(preference.getCollectorId())
                    .build());
            return preference.getInitPoint();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ResponseEntity<String> processPayment(String dataId, Long userId) {
        List<UserIdPreference> secretTokens = userIdPreferenceRepository.findByUserId(userId);

        String url = "https://api.mercadopago.com/v1/payments/" + dataId + "?access_token=" + secretTokens.getFirst().getToken();

        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> paymentDetails = restTemplate.getForObject(url, Map.class);

            // Procesa los detalles del pago
            System.out.println("Payment details: " + paymentDetails);

            // Aquí puedes agregar lógica adicional para validar y procesar el pago
            return ResponseEntity.ok("Payment processed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing payment");
        }

    }
}
