package com.school.fee_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Setter
@Getter
@Document(collection = "payments")
@Schema(description = "Represents a payment made by a student.")
public class Payment {
    @Id
    private String id;
    private String studentId;
    private String catalogId;
    private double originalAmount;
    private double amountDue;
    private double paidAmount = 0.0;
    private double fine = 0.0;
    private Date dueDate;
    private String status; // e.g., "PAID", "PARTIAL", "PENDING"
    private PaymentPlan paymentPlan;
    private Date paymentDate;

    // Getters and setters
}


