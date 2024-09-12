package com.ronnie.products_service.controllers;

import com.ronnie.products_service.models.dtos.BaseResponse;
import com.ronnie.products_service.models.dtos.OrderItemRequest;
import com.ronnie.products_service.models.dtos.ProductRequest;
import com.ronnie.products_service.models.dtos.ProductResponse;
import com.ronnie.products_service.models.entities.Product;
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

    @PostMapping // crear productos
    public void addProduct(@RequestBody ProductRequest productRequest) {
        productService.addProduct(productRequest);
    }

    @GetMapping // ver todos los productos
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/{sku}") // conseguir un producto solo con SKU
    public Product getDataWithSku(@PathVariable("sku") String sku) {
        return inventoryService.getDataWithSku(sku);
    }

    // stock
    @GetMapping(path = "/stock/{sku}") // si esta en stock
    public boolean isInStock(@PathVariable("sku") String sku) {
        return inventoryService.isInStock(sku);
    }

    @PostMapping(path = "/in-stock") // si esta(n) en stock
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse areInstock(@RequestBody List<OrderItemRequest> orderItemRequests) {
        System.out.println(orderItemRequests);
        return inventoryService.areInStock(orderItemRequests);
    }
}
