package com.ronnie.products_service.repositories;

import com.ronnie.products_service.entities.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    List<Product> findBySkuIn(List<String> skus);
}
