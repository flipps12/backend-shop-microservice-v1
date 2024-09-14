package com.ronnie.authenticator.controllers;


import com.ronnie.authenticator.entities.dtos.UserLoginRequest;
import com.ronnie.authenticator.entities.dtos.UserRegisterRequest;
import com.ronnie.authenticator.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public boolean login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        return authService.login(userLoginRequest);
    }

    @PostMapping("/register/{jwt}")
    public boolean register(@RequestBody UserRegisterRequest userRegisterRequest, @PathVariable String jwt) {
        return authService.register(jwt, userRegisterRequest);
    }

    @GetMapping("/authenticate/{jwt}/{username}")
    public Boolean authWithJwt(@PathVariable String jwt, @PathVariable String username) {
        return authService.authWithJwt(jwt, username);
    }

    // proteger por spring secutity
    @GetMapping("/generate/{username}/{role}")
    public String createJwt(@PathVariable String username, @PathVariable String role) {
        return authService.createJwt(username, role);
    }

    @GetMapping("/extract/{cookie}")
    public String extractJwt(@PathVariable String cookie) {
        return authService.extractClaims(cookie);
    }
}
