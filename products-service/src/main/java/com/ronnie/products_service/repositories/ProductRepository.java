package com.ronnie.products_service.repositories;

import com.ronnie.products_service.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
