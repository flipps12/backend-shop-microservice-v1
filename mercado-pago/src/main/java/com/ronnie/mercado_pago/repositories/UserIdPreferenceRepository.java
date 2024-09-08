package com.ronnie.mercado_pago.repositories;

import com.ronnie.mercado_pago.models.entities.MercadoPagoSellers;
import com.ronnie.mercado_pago.models.entities.UserIdPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface
UserIdPreferenceRepository extends JpaRepository<UserIdPreference, Long> {
    List<UserIdPreference> findByUserId(Long userId);
}
