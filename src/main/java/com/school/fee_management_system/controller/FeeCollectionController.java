package com.school.fee_management_system.controller;

import com.school.fee_management_system.Response.ApiResponseDetails;
import com.school.fee_management_system.Exception.BadRequestException;
import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.service.FeeCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
            @RequestParam String catalogId) {
        if (studentId == null || studentId.isEmpty()) {
            throw new BadRequestException("Student ID cannot be null or empty");
        }
        if (catalogId == null || catalogId.isEmpty()) {
            throw new BadRequestException("Catalog ID cannot be null or empty");
        }
        Receipt receipt = feeCollectionService.collectFee(studentId, catalogId);
        return ResponseEntity.ok(new ApiResponseDetails<>(true, 200,"Fee collected successfully", receipt));
    }
}
