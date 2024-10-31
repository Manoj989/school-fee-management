package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Catalog;
import com.school.fee_management_system.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Catalog createCatalog(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public String updateCatalog(Catalog catalog) {
        Optional<Catalog> catalogDetails=catalogRepository.findById(catalog.getId());
        if(catalogDetails.isPresent()){
            return "Catalog Details already present";
        }
        else{
             catalogRepository.save(catalog);
            return "Catalog Updated Successfully";
        }
    }

    @Override
    public Catalog getCatalogById(String id) {
        return catalogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Catalog not found"));
    }
    @Override
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
