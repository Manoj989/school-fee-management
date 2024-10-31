package com.school.fee_management_system.Exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorDetails {
    private int status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ErrorDetails(int status, String message, String details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.details = details;
    }

}
