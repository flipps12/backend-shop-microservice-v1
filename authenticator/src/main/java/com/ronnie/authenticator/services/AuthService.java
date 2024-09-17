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


    public String createJwt(String username, String password) {
        var user = sellersRepository.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("El usuario no existe");
        String roleDb = user.get().getRole();

        return jwtUtil.generateToken(username, roleDb);
    }

    public Boolean authWithJwt(String jwt, String username) {
        return jwtUtil.validateToken(jwt, username);
    }

    public String login(UserLoginRequest userLoginRequest) throws Exception { // login con encriptacion
        Optional<User> user = sellersRepository.findByUsername(userLoginRequest.getUsername());
        if (user.isEmpty()) return "user";

        String decrypted = AESUtil.decrypt(user.get().getPassword());
        if (decrypted.equals(userLoginRequest.getPassword())) return createJwt(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        return "password";
    }

    public boolean register(String jwt, UserRegisterRequest userRegisterRequest) throws Exception { // crear usuario si tenes un jwt especifico
        String username = jwtUtil.extractUsername(jwt);
        String adminUsername = sellersRepository.findByRole("admin").getFirst().getUsername();

        if (sellersRepository.findByUsername(userRegisterRequest.getUsername()).isPresent()) return false;

        String token = AESUtil.encrypt(userRegisterRequest.getToken()); // encriptar token
        String password = AESUtil.encrypt(userRegisterRequest.getPassword());

        if (username.equals(adminUsername)) {
            User user = User.builder()
                    .password(password)
                    .token(token)
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

    public String extractClaimsUsername(String cookie) {
        return sellersRepository.findByUsername(jwtUtil.extractUsername(cookie))
                .get().getUsername();
    }
}
