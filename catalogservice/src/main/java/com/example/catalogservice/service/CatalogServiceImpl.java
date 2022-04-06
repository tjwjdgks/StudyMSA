package com.example.catalogservice.service;


import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.dto.ResponseCatalog;
import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public List<ResponseCatalog> getAllCatalogs() {
        List<ResponseCatalog> response = new ArrayList<>();
        Iterable<CatalogEntity> catalogEntities = catalogRepository.findAll();
        catalogEntities.forEach(c-> response
                .add(ResponseCatalog.builder()
                        .productId(c.getProductId())
                        .stock(c.getStock())
                        .productName(c.getProductName())
                        .unitPrice(c.getUnitPrice())
                        .build())
        );
        return response;
    }
}
