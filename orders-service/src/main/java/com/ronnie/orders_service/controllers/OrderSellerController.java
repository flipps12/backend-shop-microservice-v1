package com.ronnie.orders_service.controllers;

import com.ronnie.orders_service.models.entities.Orders;
import com.ronnie.orders_service.repositories.OrderRepository;
import com.ronnie.orders_service.services.OrderSellerService;
import com.ronnie.orders_service.services.OrderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/order/seller")
@RequiredArgsConstructor
public class OrderSellerController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderSellerService orderSellerService;

    @GetMapping("/{name}") // conseguir ordenes por vendedor
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> getOrdersBySeller(@PathVariable("name") String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                orderSellerService.authCookie(cookie.getValue());
                return orderService.getOrdersBySeller(name); // auth de cookie
            }
        }
        return null;
    }

    @GetMapping // ver todos las ordenes
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> getOrders(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                orderSellerService.authCookie(cookie.getValue());
                return orderRepository.findAll();// auth de cookie
            }
        }
        return null;
    }


    // eliminar orders
    // demas
}
