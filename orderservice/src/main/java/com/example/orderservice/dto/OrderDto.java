package com.example.orderservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
    private LocalDateTime createdAt;

}
