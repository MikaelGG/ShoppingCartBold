package com.ecommerce.services;

import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashIntegrityBoldService {

    @Value("secretKey")
    private String secretKey;

    public String generateIntegrityHash(String idInternal, Long amount, String currency) {
        String cadenaConcatenada = idInternal + amount + currency + secretKey;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashbytes = digest.digest(cadenaConcatenada.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashbytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando hash de integridad Bold", e);
        }
    }

}
