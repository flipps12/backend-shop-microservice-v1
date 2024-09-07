package com.ronnie.orders_service.models.dtos;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;

    private String sku;
    private String seller;
    private String name;
    private String description;
    private Double price;
    private Long limitPerOrder;
    private Long stock;
    private Boolean StockNecessary;
    private Boolean status;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seller='" + seller + '\'' +
                ", description='" + description + '\'' +
                "}";
    }
}
