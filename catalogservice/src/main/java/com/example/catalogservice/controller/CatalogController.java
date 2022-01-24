package com.example.catalogservice.controller;


import com.example.catalogservice.dto.CatalogDto;
import com.example.catalogservice.dto.ResponseCatalog;
import com.example.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final WebServerApplicationContext webServerApplicationContext;
    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String status(){
        return "It's Working in User Service" + webServerApplicationContext.getWebServer().getPort();
    }

    @GetMapping("/catalogs")
    public ResponseEntity getCatalogs(){

        List<ResponseCatalog> userByAll = catalogService.getAllCatalogs();

        return ResponseEntity.status(HttpStatus.OK).body(userByAll);
    }
}
