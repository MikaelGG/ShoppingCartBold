package com.ecommerce.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InitButtonRequest {

    private Long amount;
    private String currency;
    private Long idClient;
    private Long idAddress;
    private List<ProductBoldDTO> products = new ArrayList<>();

    @Data
    public static class ProductBoldDTO {
        private String code;
        private String photo;
        private String name;
        private Long quantity;
        private Double price;
    }

}
