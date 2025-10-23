package com.ecommerce.controllers;

import com.ecommerce.services.webhooksBoldService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/webhooks/bold")
public class webhooksBoldRESTController {

    @Autowired
    webhooksBoldService webhooksBoldService;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody(required = false) String payload, @RequestParam Map<String, String> formParams,
                                                @RequestHeader Map<String, String> headers) throws IOException {
        log.info("📦 payload: {}", payload);
        log.info("🧾 formParams: {}", formParams);
        log.info("📫 headers: {}", headers);
        return ResponseEntity.status(HttpStatus.OK).body(webhooksBoldService.handleWebhook(payload));
    }

}
