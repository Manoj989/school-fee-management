package com.school.fee_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@Document(collection = "catalogs")
@Schema(description = "Represents a catalog of fees for different services or products.")
public class Catalog {
    @Id
    @Schema(description = "The unique identifier of the catalog.", example = "catalog1", readOnly = true)
    private String id;

    @Schema(description = "The name of the catalog.", example = "Annual Tution Fees")
    private String name;

    @Schema(description = "The fee associated with the catalog.", example = "100000")
    private double fee;

    @Schema(description = "The educational program associated with the catalog.", example = "PRIMARY_SCHOOL")
    private EducationalProgram program;

}
