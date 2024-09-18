package com.ronnie.products_service.controllers;

import com.ronnie.products_service.entities.dtos.BaseResponse;
import com.ronnie.products_service.entities.dtos.OrderItemRequest;
import com.ronnie.products_service.entities.dtos.ProductRequest;
import com.ronnie.products_service.entities.dtos.ProductResponse;
import com.ronnie.products_service.entities.models.Product;
import com.ronnie.products_service.services.InventoryService;
import com.ronnie.products_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final InventoryService inventoryService;

    @GetMapping("/status")
    public String status() {
        return "online";
    }

    @GetMapping(path = "/{id}") // conseguir un producto solo con SKU
    public Product getDataWithSku(@PathVariable("id") Long id) {
        return inventoryService.getDataWithId(id);
    }

    // stock
    @GetMapping(path = "/stock/{id}") // si esta en stock
    public boolean isInStock(@PathVariable("id") Long id) {
        return inventoryService.isInStock(id);
    }

    @PostMapping(path = "/in-stock") // si esta(n) en stock
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInstock(@RequestBody List<OrderItemRequest> orderItemRequests) {
        return inventoryService.areInStock(orderItemRequests);
    }
}
