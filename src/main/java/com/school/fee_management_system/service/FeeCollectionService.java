package com.school.fee_management_system.service;

import com.school.fee_management_system.Response.UnpaidStudentsResponse;
import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.model.PaymentPlan;

import java.util.Date;
import java.util.List;

public interface FeeCollectionService {
    Receipt collectFee(String studentId, String catalogId, double amountPaid, PaymentPlan paymentPlan);
    double getTotalFeesCollectedBetween(Date startDate, Date endDate);
    List<UnpaidStudentsResponse> getUnpaidPayments();
    double getDueAmountForStudent(String studentId);
    void sendReminders();
}
