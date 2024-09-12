package com.ronnie.orders_service.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MercadoPagoPreferenceItemsRequest {
    // items
    private String id;
    private String title;
    private Double price;
    private String description;
    private Long quantity;
    private String pictureUrl;
}
