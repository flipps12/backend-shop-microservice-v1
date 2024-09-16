package com.ronnie.products_service.services;

import com.ronnie.products_service.entities.dtos.BaseResponse;
import com.ronnie.products_service.entities.dtos.OrderItemRequest;
import com.ronnie.products_service.entities.models.Product;
import com.ronnie.products_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    public boolean isInStock(Long id) {
        var inventory = productRepository.findById(id);

        return inventory.filter(value -> {
            if (value.getStock() > 0) return true;
            if (!value.getStockNecessary()) {
                inventory.get();
            }
            return false;
        }).isPresent(); // poco necesario
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {

        var errorList = new ArrayList<String>();

        List<Long> ids = orderItems.stream().map(OrderItemRequest::getId).toList();
        List<Product> productList = productRepository.findByIdIn(ids);

        orderItems.forEach(orderItemRequest -> {
            var product = productList.stream().filter(value -> value.getId().equals(orderItemRequest.getId())).findFirst();
            if (product.isEmpty()) {
                errorList.add("Product with id " + orderItemRequest.getId() + " does not exist");
            } else if (product.get().getStock() < orderItemRequest.getQuantity() && product.get().getStockNecessary()) {
                System.out.println(product.get());
                errorList.add("Product with id " + orderItemRequest.getId() + " has insufficient quantity");
            } else if (product.get().getLimitPerOrder() < orderItemRequest.getQuantity()) {
                errorList.add("Product with id " + orderItemRequest.getId() + " push the limit");
            }
        });


        return !errorList.isEmpty() ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);
    }

    public Product getDataWithId(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }
}
