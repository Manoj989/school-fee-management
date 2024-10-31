package com.school.fee_management_system.service;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceiptServiceImplTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReceiptByOrderId_Success() {
        // Arrange
        String orderId = "receipt3";
        Receipt receipt = new Receipt();
        receipt.setOrderId(orderId);
        when(receiptRepository.findByOrderId(orderId)).thenReturn(receipt);

        // Act
        Receipt result = receiptService.getReceiptByOrderId(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        verify(receiptRepository, times(2)).findByOrderId(orderId);
    }

    @Test
    void testGetReceiptByOrderId_NotFound() {
        // Arrange
        String orderId = "12345";
        when(receiptRepository.findByOrderId(orderId)).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            receiptService.getReceiptByOrderId(orderId);
        });

        assertEquals("Receipt Details Not Found", exception.getMessage());
        verify(receiptRepository, times(1)).findByOrderId(orderId);
    }
}
