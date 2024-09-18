package com.ronnie.api_gateway.entities.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatusResponse {
    private String gateway;
    private String auth;
    private String mercado_pago;
    private String notification;
    private String order;
    private String product;
}
