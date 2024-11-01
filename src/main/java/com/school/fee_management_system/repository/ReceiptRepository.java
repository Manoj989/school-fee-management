package com.school.fee_management_system.repository;


import com.school.fee_management_system.model.Receipt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReceiptRepository extends MongoRepository<Receipt, String> {
}
