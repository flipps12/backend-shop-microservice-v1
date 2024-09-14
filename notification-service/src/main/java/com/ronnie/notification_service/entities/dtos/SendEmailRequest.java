package com.ronnie.notification_service.entities.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SendEmailRequest {
    private String to;
    private String subject;
    private String text;
}
