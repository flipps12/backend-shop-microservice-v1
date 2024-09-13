package com.ronnie.authenticator.controllers;


import com.ronnie.authenticator.entities.dtos.UserLoginRequest;
import com.ronnie.authenticator.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public boolean login(@RequestBody UserLoginRequest userLoginRequest) {
        return authService.login(userLoginRequest);
    }

    @GetMapping("/generate/{username}/{role}")
    public String createJwt(@PathVariable String username, @PathVariable String role) {
        return authService.createJwt(username, role);
    }


    @GetMapping("/authenticate/{jwt}/{username}")
    public Boolean authWithJwt(@PathVariable String jwt, @PathVariable String username) {
        return authService.authWithJwt(jwt, username);
    }

}
