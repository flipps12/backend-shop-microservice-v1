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
    public String login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        return authService.login(userLoginRequest);
    }

    @PostMapping("/register/{jwt}")
    public boolean register(@RequestBody UserRegisterRequest userRegisterRequest, @PathVariable String jwt) throws Exception {
        return authService.register(jwt, userRegisterRequest);
    }

    @GetMapping("/authenticate/{jwt}/{username}")
    public Boolean authWithJwt(@PathVariable String jwt, @PathVariable String username) {
        return authService.authWithJwt(jwt, username);
    }

    // proteger por spring secutity o agregar password para autenticar aca direto
//    @GetMapping("/generate/{username}/{password}")
//    public String createJwt(@PathVariable String username, @PathVariable String password) {
//        return authService.createJwt(username, password);
//    }

    @GetMapping("/extract/{cookie}")
    public String extractJwt(@PathVariable String cookie) {
        return authService.extractClaims(cookie);
    }

    @GetMapping("/extract/username/{cookie}")
    public String extractJwtUsername(@PathVariable String cookie) {
        return authService.extractClaimsUsername(cookie);
    }
}
