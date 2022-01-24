package com.example.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {

    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer totalPrice;

    @Builder
    public ResponseCatalog(String productId, String productName, Integer unitPrice, Integer totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
