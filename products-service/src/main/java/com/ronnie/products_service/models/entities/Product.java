package com.ronnie.products_service.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
