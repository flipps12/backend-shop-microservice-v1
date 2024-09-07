package com.ronnie.mercado_pago.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PreferenceControllerRequest {

    private String externalReference;

    // items
    private String title;
    private Double price;
    private String description;

    // payer
    private String email;

    // back urls
    private String successUrl;
    private String pendingUrl;
    private String failureUrl;
}
