package com.school.fee_management_system.controller;

import com.school.fee_management_system.Response.ApiResponseDetails;
import com.school.fee_management_system.Exception.BadRequestException;
import com.school.fee_management_system.model.Catalog;
import com.school.fee_management_system.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @Operation(summary = "Create a new catalog", description = "Adds a new catalog to the system")
    @ApiResponse(responseCode = "200", description = "Catalog created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDetails.class)))
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDetails<Catalog>> createCatalog(@RequestBody Catalog catalog) {
        if (catalog == null || catalog.getName() == null) {
            throw new BadRequestException("Catalog or Catalog name cannot be null");
        }
        Catalog createdCatalog = catalogService.createCatalog(catalog);
        return ResponseEntity.ok(new ApiResponseDetails<>(true, 200, "Catalog created successfully", createdCatalog));
    }

    @Operation(summary = "Update an existing catalog", description = "Updates an existing catalog")
    @ApiResponse(responseCode = "200", description = "Catalog updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDetails.class)))
    @PutMapping("/update")
    public ResponseEntity<ApiResponseDetails<String>> updateCatalog(@RequestBody Catalog catalog) {
        if (catalog == null || catalog.getId() == null) {
            throw new BadRequestException("Catalog or Catalog ID cannot be null");
        }
        String result = catalogService.updateCatalog(catalog);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "Catalog updated successfully", result));
    }

    @Operation(summary = "Get a catalog by ID", description = "Retrieves a catalog by its ID")
    @ApiResponse(responseCode = "200", description = "Catalog found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDetails.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDetails<Catalog>> getCatalogById(@PathVariable String id) {
        if (id == null) {
            throw new BadRequestException("Catalog ID cannot be null");
        }
        Catalog catalog = catalogService.getCatalogById(id);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "Catalog retrieved successfully", catalog));
    }

    @Operation(summary = "Get all catalogs", description = "Retrieves all catalogs")
    @ApiResponse(responseCode = "200", description = "Catalogs retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDetails.class)))
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDetails<List<Catalog>>> getAllCatalogs() {
        List<Catalog> catalogs = catalogService.getAllCatalogs();
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "All catalogs retrieved successfully", catalogs));
    }
}
