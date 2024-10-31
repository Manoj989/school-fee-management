package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptServiceImpl implements ReceiptService{
    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public Receipt getReceiptByOrderId(String orderId) {
        if(receiptRepository.findByOrderId(orderId)==null)
            throw new ResourceNotFoundException("Receipt Details Not Found");
        return receiptRepository.findByOrderId(orderId);
    }
}
