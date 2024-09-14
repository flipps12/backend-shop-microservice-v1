package com.ronnie.orders_service.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SendMail {
    private String to;
    private String subject;
    private String text;
}
