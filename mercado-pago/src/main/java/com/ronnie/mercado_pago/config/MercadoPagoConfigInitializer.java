package com.ronnie.mercado_pago.config;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfigInitializer {
    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken("TEST-3482883912889909-042018-d55e59102d83a57c4b5f380bc654eb4c-747866404");
    }
}
