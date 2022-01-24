package com.example.userservice.vo;

import java.time.LocalDateTime;

public class ResponseOrder extends ResponseDefault {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;

    private String orderId;
}
