package com.ronnie.orders_service.repositories;

import com.ronnie.orders_service.models.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
