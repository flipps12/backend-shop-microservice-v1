package com.ronnie.orders_service.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderNumber;
    private String seller;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrdersItems> ordersItemsList;
}
