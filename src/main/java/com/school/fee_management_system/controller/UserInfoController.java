package com.school.fee_management_system.controller;

import com.school.fee_management_system.Response.ApiResponseDetails;
import com.school.fee_management_system.Exception.BadRequestException;
import com.school.fee_management_system.model.User;
import com.school.fee_management_system.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "Get User by ID", description = "Retrieves a user by their ID")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponseDetails<User>> getUser(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("User ID cannot be null or empty");
        }
        User user = userInfoService.getUserById(id);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "User retrieved successfully", user));
    }

    @Operation(summary = "Get User by Email", description = "Retrieves a user by their email address")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponseDetails<User>> getUserByEmail(@PathVariable String email) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email cannot be null or empty");
        }
        User user = userInfoService.getUserByEmail(email);
        return ResponseEntity.ok(new ApiResponseDetails<>(true, 200,"User retrieved successfully", user));
    }

    @Operation(summary = "Create User", description = "Creates a new user record")
    @ApiResponse(responseCode = "200", description = "User created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDetails<String>> createUser(@RequestBody User user) {
        if (user == null || user.getId() == null || user.getId().isEmpty()) {
            throw new BadRequestException("User or User ID cannot be null or empty");
        }
        String result = userInfoService.createUser(user);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "User created successfully", result));
    }

    @Operation(summary = "Update User", description = "Updates an existing user record")
    @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @PostMapping("/update")
    public ResponseEntity<ApiResponseDetails<String>> updateUser(@RequestBody User user) {
        if (user == null || user.getId() == null || user.getId().isEmpty()) {
            throw new BadRequestException("User or User ID cannot be null or empty");
        }
        String result = userInfoService.updateUser(user);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "User updated successfully", result));
    }
}
