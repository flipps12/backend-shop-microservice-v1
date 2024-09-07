package com.ronnie.mercado_pago.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MercadoPagoPreferenceItemsRequest {
    // items
    private String title;
    private Double price;
    private String description;
    private Long quantity;
}
