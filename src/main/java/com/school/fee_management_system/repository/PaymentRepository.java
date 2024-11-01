package com.school.fee_management_system.repository;


import com.school.fee_management_system.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface PaymentRepository extends MongoRepository<Payment, String> {
   Payment findByStudentId(String studentId);
   List<Payment> findByStudentIdAndStatus(String studentId, String status);
   List<Payment> findByDueDateBeforeAndStatus(Date date, String status);
   List<Payment> findByPaymentDateBetween(Date startDate, Date endDate);

   List<Payment> findByStatus(String pending);

   List<Payment> findByStudentIdAndCatalogId(String studentId,String catalogId);
}
