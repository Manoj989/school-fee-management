package com.school.fee_management_system.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UnpaidStudentsResponse {
    public UnpaidStudentsResponse() {
    }
    private String studentId;
    private String studentName;
    private String studentEmail;
    private String courseName;
    private double dueAmount;
}
