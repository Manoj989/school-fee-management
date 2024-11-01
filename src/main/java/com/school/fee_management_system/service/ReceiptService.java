package com.school.fee_management_system.service;

import com.school.fee_management_system.model.Receipt;
import org.springframework.stereotype.Service;

@Service
public interface ReceiptService {
    Receipt getReceiptByReceiptId(String orderId);
}
