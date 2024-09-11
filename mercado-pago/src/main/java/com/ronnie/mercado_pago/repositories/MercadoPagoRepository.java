package com.ronnie.mercado_pago.repositories;

import com.ronnie.mercado_pago.models.entities.MercadoPagoSellers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MercadoPagoRepository extends JpaRepository<MercadoPagoSellers, Long> {
    Optional<MercadoPagoSellers> findBySeller(String seller);
}
