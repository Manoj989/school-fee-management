package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Payment;
import com.school.fee_management_system.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public String paymentStatus(String studentId) {
        Payment payment=paymentRepository.findByStudentId(studentId);
        if(payment==null){
            throw new ResourceNotFoundException("Payment Details not found");
        }
        return payment.getStatus();
    }
}
