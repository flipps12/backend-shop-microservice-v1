package com.ronnie.mercado_pago.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserId")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String token;
}
