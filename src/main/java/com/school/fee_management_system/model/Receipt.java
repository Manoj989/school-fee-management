package com.school.fee_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Setter
@Getter
@Document(collection = "receipts")
@Schema(description = "Represents a receipt for a payment made.")
public class Receipt {
    @Id
    @Schema(description = "The unique identifier of the receipt.", example = "receipt1")
    private String id;

    @Schema(description = "The order ID associated with this receipt.", example = "order1")
    private String orderId;

    @Schema(description = "The payment ID associated with this receipt.", example = "payment1")
    private String paymentId;

    @Schema(description = "The date when the receipt was issued.", example = "2024-10-31T13:30:09.613+05:30")
    private Date date;

    @Schema(description = "The type of fee payment.", example = "Tution Fees")
    private String receiptType;

    @Schema(description = "The amount paid when the receipt was issued.", example = "150.00")
    private double amount;

}
