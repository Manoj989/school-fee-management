package com.school.fee_management_system.service;

import com.school.fee_management_system.model.Receipt;
import com.school.fee_management_system.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void getReceiptByOrderId_existingOrderId_returnsReceipt() {
        // Arrange
        String orderId = "order123";
        Receipt expectedReceipt = new Receipt();
        expectedReceipt.setId("rec123");
        expectedReceipt.setOrderId(orderId);
        when(receiptRepository.findByOrderId(orderId)).thenReturn(expectedReceipt);

        // Act
        Receipt actualReceipt = receiptService.getReceiptByOrderId(orderId);

        // Assert
        assertNotNull(actualReceipt);
        assertEquals(expectedReceipt, actualReceipt);
        verify(receiptRepository).findByOrderId(orderId);
    }

    @Test
    void getReceiptByOrderId_nonExistingOrderId_returnsNull() {
        // Arrange
        String orderId = "nonExistingOrder";
        when(receiptRepository.findByOrderId(orderId)).thenReturn(null);

        // Act
        Receipt actualReceipt = receiptService.getReceiptByOrderId(orderId);

        // Assert
        assertNull(actualReceipt);
        verify(receiptRepository).findByOrderId(orderId);
    }
}
