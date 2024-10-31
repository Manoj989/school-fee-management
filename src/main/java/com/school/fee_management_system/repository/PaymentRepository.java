package com.school.fee_management_system.repository;


import com.school.fee_management_system.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PaymentRepository extends MongoRepository<Payment, String> {
   Payment findByStudentId(String studentId);
}
