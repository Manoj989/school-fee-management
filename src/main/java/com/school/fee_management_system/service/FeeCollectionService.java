package com.school.fee_management_system.service;

import com.school.fee_management_system.model.Receipt;
import org.springframework.stereotype.Service;

@Service
public interface FeeCollectionService {
    Receipt collectFee(String studentId, String catalogId);
}
