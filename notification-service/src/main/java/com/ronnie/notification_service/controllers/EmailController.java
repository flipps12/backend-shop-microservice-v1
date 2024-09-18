package com.ronnie.notification_service.controllers;

import com.ronnie.notification_service.entities.dtos.SendEmailRequest;
import com.ronnie.notification_service.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/status")
    public String status() {
        return "online";
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody SendEmailRequest sendEmailRequest) {
        emailService.sendEmail(sendEmailRequest.getTo(), sendEmailRequest.getSubject(), sendEmailRequest.getText());
        return "Correo enviado con éxito";
    }
}
