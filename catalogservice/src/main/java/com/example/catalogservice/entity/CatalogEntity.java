package com.example.catalogservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "catalog")
@NoArgsConstructor
public class CatalogEntity {

    @Id @GeneratedValue
    Long id;

    @Column(nullable = false,unique = true)
    private String productId;

    @Column(nullable = false)
    private String productName;

    private int stock;

    @Column(nullable = false)
    private Integer unitPrice;

    private LocalDateTime createdAt = LocalDateTime.now();

    public CatalogEntity(String productId, String productName, int stock, Integer unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }
}
