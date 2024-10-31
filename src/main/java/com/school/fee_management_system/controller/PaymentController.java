package com.school.fee_management_system.controller;

import com.school.fee_management_system.Response.ApiResponseDetails;
import com.school.fee_management_system.Exception.BadRequestException;
import com.school.fee_management_system.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    PaymentServiceImpl paymentService;

    @Operation(summary = "Get Student Payment Status", description = "Retrieves the payment status for a given student by their ID")
    @ApiResponse(responseCode = "200", description = "Payment status retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @GetMapping("/status/{studentid}")
    public ResponseEntity<ApiResponseDetails<String>> getStudentPaymentStatus(@PathVariable String studentid) {
        if (studentid == null || studentid.isEmpty()) {
            throw new BadRequestException("Student ID cannot be null or empty");
        }
        String status = paymentService.paymentStatus(studentid);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "Payment status retrieved successfully", status));
    }
}
