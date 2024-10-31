package com.school.fee_management_system.service;

import com.school.fee_management_system.model.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    Student getStudent(String studentId);
    String addStudent(Student student);
    String updateStudentDetails(Student student);
}
