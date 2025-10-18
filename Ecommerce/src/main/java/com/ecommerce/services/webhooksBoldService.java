package com.ecommerce.services;

import com.ecommerce.models.transactionBoldModel;
import com.ecommerce.repositories.transactionBoldRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class webhooksBoldService {

    @Value("${webhookSecret}")
    private String webhookSecret;

    @Autowired
    transactionBoldRepository transactionBoldRepository;

    @Transactional
    public String handleWebhook(HttpServletRequest request) throws IOException {

        String payload = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String signature = request.getHeader("X-Bold-Signature");

        String expectedSignature = hmacSHA256(payload, webhookSecret);
        if (!expectedSignature.equalsIgnoreCase(signature)) {
            log.info("⚠️ Firma inválida");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid signature");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(payload);
        String type = json.path("type").asText();
        JsonNode data = json.path("data");

        String reference = data.path("reference").asText();
        String idPayment = data.path("id").asText();
        String status = mapBoldStatus(type);
        Long amount = data.path("amount").asLong();

        transactionBoldModel transaction = transactionBoldRepository.findByIdInternal(reference).orElse(null);
        if (transaction != null) {
            transaction.setStatus(status);
            transaction.setRawPayload(payload);
            transaction.setIdBoldOrder(idPayment);
            transactionBoldRepository.save(transaction);
        }

        log.info("✅ Webhook procesado: " + reference + " -> " + status);
        return "Webhook saved successfully";
    }

    private String hmacSHA256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error verificando firma del webhook", e);
        }
    }

    private String mapBoldStatus(String type) {
        return switch (type) {
            case "SALE_APPROVED" -> "APPROVED";
            case "SALE_DECLINED" -> "REJECTED";
            case "SALE_PENDING" -> "PENDING";
            default -> "UNKNOWN";
        };
    }

}
