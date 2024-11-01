package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public Receipt getReceiptByReceiptId(String orderId) {
        Optional<Receipt> receipt = receiptRepository.findById(orderId);
        if (receipt.isPresent()) {
            return receipt.get();
        }
        else{
            throw new ResourceNotFoundException("Receipt not found");
        }
    }
}
