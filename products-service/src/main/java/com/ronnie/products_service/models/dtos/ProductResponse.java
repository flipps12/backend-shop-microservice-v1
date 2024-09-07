package com.ronnie.products_service.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String seller;
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Long stock;
    private Long limitPerOrder;
    private Boolean StockNecessary;
    private Boolean status;
}
