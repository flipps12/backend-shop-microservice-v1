package com.ronnie.api_gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ApiGatwayController {

    @GetMapping("/status")
    public String getStatus() {
        return "online"; // añadir online de los demas servers
    }
}
