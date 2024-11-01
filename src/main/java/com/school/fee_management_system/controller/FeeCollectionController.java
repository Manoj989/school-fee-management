package com.school.fee_management_system.controller;

import com.school.fee_management_system.Response.ApiResponseDetails;
import com.school.fee_management_system.Exception.BadRequestException;
import com.school.fee_management_system.model.Payment;
import com.school.fee_management_system.model.PaymentPlan;
import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.service.FeeCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/fee")
public class FeeCollectionController {
    @Autowired
    private FeeCollectionService feeCollectionService;

    @Operation(summary = "Collect a fee", description = "Collects fees based on student and catalog IDs")
    @ApiResponse(responseCode = "200", description = "Fee collected successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @PostMapping("/collect")
    public ResponseEntity<ApiResponseDetails<Receipt>> collectFee(
            @RequestParam String studentId,
            @RequestParam String catalogId,
            @RequestParam double amountPaid,
            @RequestParam PaymentPlan paymentPlan) {
        Receipt receipt = feeCollectionService.collectFee(studentId, catalogId, amountPaid, paymentPlan);
        return ResponseEntity.ok(new ApiResponseDetails<>(true, 200,"Fee collected successfully", receipt));
    }
    @GetMapping("/unpaid")
    public ResponseEntity<Double> getUnpaidPayments() {
       double unpayment=feeCollectionService.getUnpaidPayments();
        return ResponseEntity.ok(unpayment);
    }

    @GetMapping("/due/{studentId}")
    public ResponseEntity<Double> getDueAmountForStudent(@PathVariable String studentId) {
        double dueAmount = feeCollectionService.getDueAmountForStudent(studentId);
        return ResponseEntity.ok(dueAmount);
    }

    @GetMapping("/collected")
    public ResponseEntity<Double> getTotalFeesCollected(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        double totalCollected = feeCollectionService.getTotalFeesCollectedBetween(startDate, endDate);
        return ResponseEntity.ok(totalCollected);
    }
}
