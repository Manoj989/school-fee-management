package com.school.fee_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Setter
@Getter
@Document(collection = "students")
@Schema(description = "Represents a student in the fee management system.")
public class Student {
    @Id
    @Schema(description = "The unique identifier of the student.", example = "student1")
    private String id;

    @Schema(description = "The name of the student.", example = "John Doe")
    private String name;

    @Schema(description = "The user ID associated with the student, used for login and other purposes.", example = "user1")
    private String userId;

    @Schema(description = "Email.", example = "studentemail1@gmail.com")
    private String email;

    @Schema(description = "Define the catalog ID", example = "catalog1")
    private String catalogId;


}
