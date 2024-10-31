package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Student;
import com.school.fee_management_system.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStudent_existingStudent_returnsStudent() {
        // Arrange
        String studentId = "student1";
        Student expectedStudent = new Student();
        expectedStudent.setId(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        // Act
        Student actualStudent = studentService.getStudent(studentId);

        // Assert
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getStudent_nonExistingStudent_throwsException() {
        // Arrange
        String studentId = "student2";
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudent(studentId));
    }

    @Test
    void addStudent_newStudent_returnsAdded() {
        // Arrange
        Student newStudent = new Student();
        newStudent.setId("student3");
        when(studentRepository.findById(newStudent.getId())).thenReturn(Optional.empty());

        // Act
        String result = studentService.addStudent(newStudent);

        // Assert
        assertEquals("Student added", result);
        verify(studentRepository).save(newStudent);
    }

    @Test
    void addStudent_existingStudent_returnsAlreadyExists() {
        // Arrange
        Student existingStudent = new Student();
        existingStudent.setId("student4");
        when(studentRepository.findById(existingStudent.getId())).thenReturn(Optional.of(existingStudent));

        // Act
        String result = studentService.addStudent(existingStudent);

        // Assert
        assertEquals("Student already exists", result);
        verify(studentRepository, never()).save(existingStudent);
    }

    @Test
    void updateStudentDetails_existingStudent_returnsUpdated() {
        // Arrange
        Student existingStudent = new Student();
        existingStudent.setId("student5");
        when(studentRepository.existsById(existingStudent.getId())).thenReturn(true);

        // Act
        String result = studentService.updateStudentDetails(existingStudent);

        // Assert
        assertEquals("Student updated", result);
        verify(studentRepository).save(existingStudent);
    }

    @Test
    void updateStudentDetails_nonExistingStudent_returnsNotFound() {
        // Arrange
        Student nonExistingStudent = new Student();
        nonExistingStudent.setId("student6");
        when(studentRepository.existsById(nonExistingStudent.getId())).thenReturn(false);

        // Act
        String result = studentService.updateStudentDetails(nonExistingStudent);

        // Assert
        assertEquals("Student not found", result);
        verify(studentRepository, never()).save(nonExistingStudent);
    }
}
