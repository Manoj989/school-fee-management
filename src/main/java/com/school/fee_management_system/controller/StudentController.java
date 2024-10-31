package com.school.fee_management_system.controller;

import com.school.fee_management_system.Response.ApiResponseDetails;
import com.school.fee_management_system.Exception.BadRequestException;
import com.school.fee_management_system.model.Student;
import com.school.fee_management_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @Operation(summary = "Get Student Details", description = "Retrieves details of a student by their ID")
    @ApiResponse(responseCode = "200", description = "Student details retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDetails<Student>> getStudentDetails(@PathVariable String id) {
        if (id == null || id.isEmpty()) {
            throw new BadRequestException("Student ID cannot be null or empty");
        }
        Student student = studentService.getStudent(id);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "Student details retrieved successfully", student));
    }

    @Operation(summary = "Create Student", description = "Creates a new student record")
    @ApiResponse(responseCode = "200", description = "Student created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDetails<String>> createStudent(@RequestBody Student student) {
        if (student == null || student.getId() == null || student.getId().isEmpty()) {
            throw new BadRequestException("Student or Student ID cannot be null or empty");
        }
        String result = studentService.addStudent(student);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "Student created successfully", result));
    }

    @Operation(summary = "Update Student", description = "Updates an existing student record")
    @ApiResponse(responseCode = "200", description = "Student updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponseDetails.class)))
    @PutMapping("/update")
    public ResponseEntity<ApiResponseDetails<String>> updateStudent(@RequestBody Student student) {
        if (student == null || student.getId() == null || student.getId().isEmpty()) {
            throw new BadRequestException("Student or Student ID cannot be null or empty");
        }
        String result = studentService.updateStudentDetails(student);
        return ResponseEntity.ok(new ApiResponseDetails<>(true,200, "Student updated successfully", result));
    }
}
