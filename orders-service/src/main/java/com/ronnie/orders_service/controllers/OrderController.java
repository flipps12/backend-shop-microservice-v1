package com.ronnie.orders_service.controllers;

import com.ronnie.orders_service.models.dtos.OrderRequest;
import com.ronnie.orders_service.models.entities.Orders;
import com.ronnie.orders_service.repositories.OrderRepository;
import com.ronnie.orders_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> getOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        this.orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }
}
