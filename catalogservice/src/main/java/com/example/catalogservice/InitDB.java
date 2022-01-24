package com.example.catalogservice;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import com.example.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final CatalogRepository catalogRepository;

    @PostConstruct
    public void createCatalog(){
        List<CatalogEntity> catalogEntities = new ArrayList<>();
        catalogEntities.add(new CatalogEntity("CATALOG-001","Berlin",100,150));
        catalogEntities.add(new CatalogEntity("CATALOG-002","Tokyo",110,200));
        catalogEntities.add(new CatalogEntity("CATALOG-003","Stockholm",120,300));
        catalogEntities.add(new CatalogEntity("CATALOG-004","Seoul",130,400));

        catalogRepository.saveAll(catalogEntities);
    }

}
