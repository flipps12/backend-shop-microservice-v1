package com.ronnie.products_service.controllers;

import com.ronnie.products_service.entities.dtos.ProductRequest;
import com.ronnie.products_service.entities.dtos.ProductResponse;
import com.ronnie.products_service.services.ProductSellerService;
import com.ronnie.products_service.services.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/seller")
@RequiredArgsConstructor
public class ProductSellerController {

    private final ProductSellerService productSellerService;
    private final ProductService productService;

    @PostMapping // crear productos
    public String addProduct(@RequestBody ProductRequest productRequest, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                productSellerService.authCookie(cookie.getValue());
                productService.addProduct(productRequest); // auth de cookie
                return "auth";
            }
        }
        return "no auth";
    }

    @GetMapping // ver todos los productos
    public List<ProductResponse> getAllProducts(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                productSellerService.authCookie(cookie.getValue());
                return productService.getAllProducts(); // auth de cookie
            }
        }
        return null;
    }

    @GetMapping("/products") // ver todos los productos
    public List<ProductResponse> getProducts(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) throw new IllegalArgumentException("Sin cookies");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                String username = productSellerService.viewUsername(cookie.getValue());
                return productService.getProducts(username); // auth de cookie
            }
        }
        return null;
    }

}
