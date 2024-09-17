package com.ronnie.authenticator.entities.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sellers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String token;
    private String role;
}
