package com.ronnie.orders_service.services;

import com.ronnie.orders_service.models.dtos.SendMail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MailService {


    private final WebClient.Builder webClientBuilder;

    public String sendMail(String to, String subject, String text) {
        var errorList = new ArrayList<String>();
        SendMail sendMail = SendMail.builder()
                .to(to)
                .subject(subject)
                .text(text)
                .build();

        String result =  this.webClientBuilder.build() // peticion checkear stock
                .post()
                .uri("https://localhost:8085/api/email/send")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(sendMail)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result;
    }
}
