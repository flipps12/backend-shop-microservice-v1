package com.ronnie.orders_service.controllers;

import com.ronnie.orders_service.models.dtos.OrderRequest;
import com.ronnie.orders_service.models.entities.Orders;
import com.ronnie.orders_service.repositories.OrderRepository;
import com.ronnie.orders_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/status")
    public String status() {
        return "online";
    }

    @PostMapping // crear orden
    @ResponseStatus(HttpStatus.OK)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    // PROTEGER con spring security
    @PostMapping(path = "/setStatus") // marcar como pagado
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String processPayment(@RequestBody Map<String, Object> payment) {
        return orderService.resetStatus((String) payment.get("external_reference"));
    }

    // devoted edit
}
