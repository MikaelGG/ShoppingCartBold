package com.ecommerce.dtos;

import lombok.Data;

@Data
public class InitButtonRequest {

    private Long amount;
    private String currency;
    private Long idClient;
    private Long idAddress;

}
