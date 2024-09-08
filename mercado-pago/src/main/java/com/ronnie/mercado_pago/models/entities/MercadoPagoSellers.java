package com.ronnie.mercado_pago.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sellers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MercadoPagoSellers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String seller;
}
