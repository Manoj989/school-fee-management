package com.school.fee_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@Document(collection = "users")
@Schema(description = "Represents a user in the system, which could be a teacher or administrative staff.")
public class User {
    @Id
    @Schema(description = "The unique identifier of the user.", example = "user1")
    private String id;

    @Schema(description = "The name of the user.", example = "Jane Doe")
    private String name;

    @Schema(description = "The email address of the user.", example = "jane.doe@example.com")
    private String email;

}
