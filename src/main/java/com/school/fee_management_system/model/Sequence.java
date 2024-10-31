package com.school.fee_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "sequences")
public class Sequence {
    @Id
    private String id; // This will be the name of the collection for which the sequence is maintained
    private long seq; // This will store the current value of the sequence

}
