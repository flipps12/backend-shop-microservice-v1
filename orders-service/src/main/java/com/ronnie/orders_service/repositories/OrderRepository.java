package com.ronnie.orders_service.repositories;

import com.ronnie.orders_service.models.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNumber(String orderNumber);
}
