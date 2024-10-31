package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.User;
import com.school.fee_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserInfoServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_existingUser_returnsUser() {
        // Arrange
        String userId = "user1";
        User expectedUser = new User();
        expectedUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userInfoService.getUserById(userId);

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserById_nonExistingUser_throwsException() {
        // Arrange
        String userId = "user2";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userInfoService.getUserById(userId));
    }

    @Test
    void getUserByEmail_existingEmail_returnsUser() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // Act
        User actualUser = userInfoService.getUserByEmail(email);

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserByEmail_nonExistingEmail_throwsException() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userInfoService.getUserByEmail(email));
    }

    @Test
    void createUser_newUser_returnsCreated() {
        // Arrange
        User newUser = new User();
        newUser.setId("user3");
        when(userRepository.findById(newUser.getId())).thenReturn(Optional.empty());

        // Act
        String result = userInfoService.createUser(newUser);

        // Assert
        assertEquals("User is created", result);
        verify(userRepository).save(newUser);
    }

    @Test
    void createUser_existingUser_returnsAlreadyExists() {
        // Arrange
        User existingUser = new User();
        existingUser.setId("user4");
        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        // Act
        String result = userInfoService.createUser(existingUser);

        // Assert
        assertEquals("User Already Exists", result);
        verify(userRepository, never()).save(existingUser);
    }

    @Test
    void updateUser_existingUser_returnsUpdated() {
        // Arrange
        User existingUser = new User();
        existingUser.setId("user5");
        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        // Act
        String result = userInfoService.updateUser(existingUser);

        // Assert
        assertEquals("User details updated", result);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_nonExistingUser_throwsException() {
        // Arrange
        User nonExistingUser = new User();
        nonExistingUser.setId("user6");
        when(userRepository.findById(nonExistingUser.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userInfoService.updateUser(nonExistingUser));
    }
}
