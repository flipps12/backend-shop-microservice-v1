package com.ronnie.authenticator.repositories;

import com.ronnie.authenticator.entities.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
