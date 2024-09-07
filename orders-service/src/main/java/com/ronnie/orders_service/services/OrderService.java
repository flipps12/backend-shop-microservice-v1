package com.ronnie.orders_service.services;

import com.ronnie.orders_service.config.WebClientConfig;
import com.ronnie.orders_service.models.dtos.BaseResponse;
import com.ronnie.orders_service.models.dtos.OrderItemRequest;
import com.ronnie.orders_service.models.dtos.OrderRequest;
import com.ronnie.orders_service.models.entities.Orders;
import com.ronnie.orders_service.models.entities.OrdersItems;
import com.ronnie.orders_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;


    public void placeOrder(OrderRequest orderRequest) {
        // Check stock and more
        System.out.println(orderRequest);
        System.out.println(orderRequest.getOrderItems());
        BaseResponse result =  this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8082/api/product/in-stock")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
        System.out.println(result);
        if (result != null && !result.hasError()) {

            Orders orders = new Orders();
            orders.setOrderNumber(UUID.randomUUID().toString());
            orders.setOrdersItemsList(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, orders))
                    .toList());
            orderRepository.save(orders);
        } else {
            throw new IllegalArgumentException("Sin stock");
        }
    }

    private OrdersItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Orders orders) {
        return OrdersItems.builder()
                .id(orders.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(orders)
                .build();
    }
}
