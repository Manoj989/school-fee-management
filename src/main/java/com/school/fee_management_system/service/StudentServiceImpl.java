package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Student;
import com.school.fee_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student getStudent(String studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Override
    public String addStudent(Student student) {
        Optional<Student> studentDetails=studentRepository.findById(student.getId());
        if(studentDetails.isPresent()){
            return "Student already exists";
        }else {
            studentRepository.save(student);
        }
        return "Student added";
    }

    @Override
    public String updateStudentDetails(Student student) {
        if(studentRepository.existsById(student.getId())){
            studentRepository.save(student);
            return "Student updated";
        }else{
            return "Student not found";
        }
    }
}
