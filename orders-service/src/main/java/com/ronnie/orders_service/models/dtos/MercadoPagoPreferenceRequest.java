package com.ronnie.orders_service.models.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MercadoPagoPreferenceRequest {
    private String externalReference;
    // items
    private List<MercadoPagoPreferenceItemsRequest> items;

    // payer
    private String email;
    private String name;
    private String surname;
}
