package com.ronnie.authenticator.services;

import com.ronnie.authenticator.components.AESUtil;
import com.ronnie.authenticator.components.JwtUtil;
import com.ronnie.authenticator.entities.dtos.UserLoginRequest;
import com.ronnie.authenticator.entities.dtos.UserRegisterRequest;
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
    private final AESUtil aesUtil;


    public String createJwt(String username, String role) {
        // añadir role por base de datos
        if (sellersRepository.findByUsername(username).isEmpty()) throw new IllegalArgumentException("El usuario no existe");
        return jwtUtil.generateToken(username, role);
    }

    public Boolean authWithJwt(String jwt, String username) {
        return jwtUtil.validateToken(jwt, username);
    }

    public boolean login(UserLoginRequest userLoginRequest) throws Exception { // login con encriptacion
        Optional<User> user = sellersRepository.findByUsername(userLoginRequest.getUsername());
        if (user.isEmpty()) return false;

        String decrypted = AESUtil.decrypt(user.get().getPassword());
        if (decrypted.equals(userLoginRequest.getPassword())) return true;
        return false;
    }

    public boolean register(String jwt, UserRegisterRequest userRegisterRequest) { // crear usuario si tenes un jwt especifico
        String username = jwtUtil.extractUsername(jwt);
        String adminUsername = sellersRepository.findByRole("admin").getFirst().getUsername();
        if (username.equals(adminUsername)) {
            User user = User.builder()
                    .password(userRegisterRequest.getPassword())
                    .username(userRegisterRequest.getUsername())
                    .role(userRegisterRequest.getRole())
                    .build();
            sellersRepository.save(user);
            return true;
        }
        return false;
    }

    public String extractClaims(String cookie) {
        return sellersRepository.findByUsername(jwtUtil.extractUsername(cookie))
                .get().getRole();
    }

}
