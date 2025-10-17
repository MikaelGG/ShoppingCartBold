package com.ecommerce.dtos;

import lombok.Data;

@Data
public class InitButtonResponse {

    private String apiKey;
    private String orderId;
    private Long amount;
    private String integrityHash;

}
