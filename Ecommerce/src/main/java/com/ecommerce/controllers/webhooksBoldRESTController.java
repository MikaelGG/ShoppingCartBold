package com.ecommerce.controllers;

import com.ecommerce.services.webhooksBoldService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/webhooks/bold")
public class webhooksBoldRESTController {

    @Autowired
    webhooksBoldService webhooksBoldService;

    @PostMapping
    public ResponseEntity<String> handleWebhook(HttpServletRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(webhooksBoldService.handleWebhook(request));
    }

}
