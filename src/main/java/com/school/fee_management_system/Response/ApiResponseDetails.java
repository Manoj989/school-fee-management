package com.school.fee_management_system.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponseDetails<T> {
    private boolean success;
    private String message;
    private int code;
    private T data;

    public ApiResponseDetails(boolean success, int code, String message, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
