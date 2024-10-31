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

    @Schema(description = "The name of the catalog.", example = "Tution Fees")
    private String name;

    @Schema(description = "The fee associated with the catalog.", example = "150.00")
    private double fee;

}
