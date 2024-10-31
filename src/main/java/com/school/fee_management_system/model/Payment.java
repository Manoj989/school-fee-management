package com.school.fee_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@Document(collection = "payments")
@Schema(description = "Represents a payment made by a student.")
public class Payment {
    @Id
    @Schema(description = "The unique identifier of the payment.", example = "payment1")
    private String id;

    @Schema(description = "The ID of the student who made the payment.", example = "student1")
    private String studentId;

    @Schema(description = "The amount of the payment.", example = "250.00")
    private double amount;

    @Schema(description = "The status of the payment.", example = "Completed")
    private String status;

    @Schema(description = "The type of fee payment.", example = "Tution Fees")
    private String paymentType;


}
