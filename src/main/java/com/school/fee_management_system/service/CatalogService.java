package com.school.fee_management_system.service;
import com.school.fee_management_system.model.Catalog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatalogService {
    Catalog createCatalog(Catalog catalog);
    String updateCatalog(Catalog catalog);
    Catalog getCatalogById(String id);
    List<Catalog> getAllCatalogs();
}
