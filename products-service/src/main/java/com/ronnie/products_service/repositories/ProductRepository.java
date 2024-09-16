package com.ronnie.products_service.repositories;

import com.ronnie.products_service.entities.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findByIdIn(List<Long> id);
    List<Product> findBySeller(String seller);
}
