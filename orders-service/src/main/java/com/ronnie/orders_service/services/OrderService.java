package com.ronnie.orders_service.services;

import com.ronnie.orders_service.config.WebClientConfig;
import com.ronnie.orders_service.models.dtos.*;
import com.ronnie.orders_service.models.entities.Orders;
import com.ronnie.orders_service.models.entities.OrdersItems;
import com.ronnie.orders_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final MailService mailService;

    public String resetStatus(String reference) {
        try {
            Orders order = orderRepository.findByOrderNumber(reference).orElseThrow(() -> new IllegalArgumentException("Order not found"));
            order.setPaid(true);
            orderRepository.save(order);

            // Bajar nivel de stock auto?

            // Procesar con email

            return "Lista editada";
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String resetDevoted(String reference) {
        try {
            Orders order = orderRepository.findByOrderNumber(reference).orElseThrow(() -> new IllegalArgumentException("Order not found"));
            order.setDevoted(true);
            orderRepository.save(order);

            // Procesar con email

            return "Lista editada";
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String placeOrder(OrderRequest orderRequest) {
        // Check stock and more
        System.out.println(orderRequest);
        System.out.println(orderRequest.getOrderItems());
        BaseResponse result =  this.webClientBuilder.build() // peticion checkear stock
                .post()
                .uri("http://localhost:8080/api/product/in-stock")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();


        if (result != null && !result.hasError()) {


            Orders orders = new Orders(); // generar orden
            orders.setOrderNumber(UUID.randomUUID().toString());
            orders.setSeller(orderRequest.getOrderItems().getFirst().getSeller());
            orders.setPaid(false);
            orders.setDevoted(false);
            orders.setEmail(orderRequest.getEmail());
            orders.setName(orderRequest.getName());
            orders.setSurname(orderRequest.getSurname());
            orders.setOrdersItemsList(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, orders))
                    .toList());

            List<MercadoPagoPreferenceItemsRequest> orderItems = new ArrayList<>();

            // agreagar seguridad con los productos, que sean del muismo vendedor, etc

            String resultInitPoint =  this.webClientBuilder.build() // peticion crear metodo de pago
                .post()
                .uri("http://localhost:8080/api/mercado-pago/create-preference")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(MercadoPagoPreferenceRequest.builder()
                        .externalReference(orders.getOrderNumber())
                        .email(orders.getEmail())
                        .name(orders.getName())
                        .surname(orders.getSurname())
                        .seller(orders.getSeller())
                        .items(orders.getOrdersItemsList().stream()
                                .map(this::mapOrderPreferencesItems)
                                .toList())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
            // System.out.println(resultInitPoint);
            orders.setInitPoint(resultInitPoint);
            orderRepository.save(orders);

            // mandar por mail
            System.out.println(mailService.sendMail(orderRequest.getEmail(),
                    "Pedido de compra creado",
                    "Link de pago: " + resultInitPoint)); // cambiar por HTML

            return resultInitPoint;
        } else {
            throw new IllegalArgumentException("Sin stock");
        }
    }

    // conseguir ordenes por nombre

    public List<Orders> getOrdersBySeller(String seller) {
        List<Orders> ordenes = orderRepository.findBySeller(seller);
        return ordenes;
    }

    // -- funciones secundarias

    private MercadoPagoPreferenceItemsRequest mapOrderPreferencesItems(OrdersItems ordersItemsPreference) {
        Product dataProduct = getProductData(ordersItemsPreference.getProductId());
        return MercadoPagoPreferenceItemsRequest.builder()
                .id(String.valueOf(dataProduct.getId()))
                .title(dataProduct.getName())
                .price(dataProduct.getPrice()) // pasasr a doublew
                .quantity(ordersItemsPreference.getQuantity())
                .description(dataProduct.getDescription())
                .pictureUrl(dataProduct.getPictureUrl())
                .build();
    }

    private OrdersItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Orders orders) {
        return OrdersItems.builder()
                .id(orders.getId())
                .productId(orderItemRequest.getId())
                .quantity(orderItemRequest.getQuantity())
                .order(orders)
                .build();
    }

    private Product getProductData(Long id) {
        return webClientBuilder.build() // peticion checkear stock
                .get()
                .uri("http://localhost:8080/api/product/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
