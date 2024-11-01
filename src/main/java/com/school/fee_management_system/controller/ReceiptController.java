package com.school.fee_management_system.controller;

import com.school.fee_management_system.Response.ApiResponseDetails;
import com.school.fee_management_system.Exception.BadRequestException;
import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @Operation(summary = "Get Receipt by Order ID", description = "Retrieves a receipt based on the order ID provided")
    @ApiResponse(responseCode = "200", description = "Receipt retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @GetMapping("/{receiptid}")
    public ResponseEntity<ApiResponseDetails<Receipt>> getReceipt(@PathVariable String receiptid) {
        if (receiptid == null || receiptid.isEmpty()) {
            throw new BadRequestException("Order ID cannot be null or empty");
        }
        Receipt receipt = receiptService.getReceiptByReceiptId(receiptid);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "Receipt retrieved successfully", receipt));
    }
}
