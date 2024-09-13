package com.ronnie.authenticator.services;

import com.ronnie.authenticator.components.JwtUtil;
import com.ronnie.authenticator.entities.dtos.UserLoginRequest;
import com.ronnie.authenticator.entities.models.User;
import com.ronnie.authenticator.repositories.SellersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final SellersRepository sellersRepository;

    public String createJwt(String username, String role) {
        if (sellersRepository.findByUsername(username).isEmpty()) throw new IllegalArgumentException("El usuario no existe");
        return jwtUtil.generateToken(username, role);
    }

    public Boolean authWithJwt(String jwt, String username) {
        return jwtUtil.validateToken(jwt, username);
    }

    public boolean login(UserLoginRequest userLoginRequest) {
        Optional<User> user = sellersRepository.findByUsername(userLoginRequest.getUsername());
        // agregar cifrado

        if (user.isEmpty()) return false;
        if (user.get().getPassword().equals(userLoginRequest.getPassword())) return true;
        return false;
    }

    // agregar register
}
