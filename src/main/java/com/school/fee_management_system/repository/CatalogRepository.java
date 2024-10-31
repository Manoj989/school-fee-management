package com.school.fee_management_system.repository;


import com.school.fee_management_system.model.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogRepository extends MongoRepository<Catalog, String> {}
