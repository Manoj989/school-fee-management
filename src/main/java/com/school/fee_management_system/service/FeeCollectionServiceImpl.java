package com.school.fee_management_system.service;

import com.school.fee_management_system.model.*;
import com.school.fee_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class FeeCollectionServiceImpl implements FeeCollectionService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private SequenceService sequenceService;

    @Transactional
    @Override
    public Receipt collectFee(String studentId, String catalogId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() -> new RuntimeException("Catalog not found"));

        long nextPaymentId = sequenceService.getNextSequence("payment");
        Payment payment = new Payment();
        payment.setId("payment" + nextPaymentId);
        payment.setStudentId(student.getId());
        payment.setAmount(catalog.getFee());
        payment.setPaymentType(catalog.getName());
        payment.setStatus("PAID");
        paymentRepository.save(payment);

        long nextReceiptId = sequenceService.getNextSequence("receipt");
        Receipt receipt = new Receipt();
        receipt.setOrderId("receipt" + nextReceiptId);
        receipt.setPaymentId(payment.getId());
        receipt.setReceiptType(catalog.getName());
        receipt.setDate(new Date());
        receiptRepository.save(receipt);

        return receipt;
    }
}
