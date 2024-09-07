package com.ronnie.products_service.services;

import com.ronnie.products_service.models.dtos.BaseResponse;
import com.ronnie.products_service.models.dtos.OrderItemRequest;
import com.ronnie.products_service.models.entities.Product;
import com.ronnie.products_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    public boolean isInStock(String sku) {
        var inventory = productRepository.findBySku(sku);

        return inventory.filter(value -> value.getStock() > 0 || !value.getStockNecessary() && value.getLimitPerOrder() < inventory.get().getLimitPerOrder()).isPresent(); // poco necesario
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {

        var errorList = new ArrayList<String>();

        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();
        List<Product> productList = productRepository.findBySkuIn(skus);

        orderItems.forEach(orderItemRequest -> {
            var product = productList.stream().filter(value -> value.getSku().equals(orderItemRequest.getSku())).findFirst();
            if (product.isEmpty()) {
                errorList.add("Product with sku " + orderItemRequest.getSku() + " does not exist");
            } else if (product.get().getStock() < orderItemRequest.getQuantity() && product.get().getStockNecessary()) {
                System.out.println(product.get());
                errorList.add("Product with sku " + orderItemRequest.getSku() + " has insufficient quantity");
            } else if (product.get().getLimitPerOrder() < orderItemRequest.getQuantity()) {
                errorList.add("Product with sku " + orderItemRequest.getSku() + " push the limit");
            }
        });


        return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }

    public Double getDataWithSku(String sku) {
        var product = productRepository.findBySku(sku).get();
        return product.getPrice();
    }
}
