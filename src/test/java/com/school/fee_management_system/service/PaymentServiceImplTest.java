package com.school.fee_management_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.school.fee_management_system.Exception.ResourceNotFoundException;
import com.school.fee_management_system.model.Payment;
import com.school.fee_management_system.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PaymentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PaymentServiceImplTest {
    @MockBean
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    /**
     * Method under test: {@link PaymentServiceImpl#paymentStatus(String)}
     */
    @Test
    void testPaymentStatus() {
        // Arrange
        Payment payment = new Payment();
        payment.setAmount(10.0d);
        payment.setId("42");
        payment.setStatus("Status");
        payment.setStudentId("42");
        when(paymentRepository.findByStudentId(Mockito.<String>any())).thenReturn(payment);

        // Act
        String actualPaymentStatusResult = paymentServiceImpl.paymentStatus("42");

        // Assert
        verify(paymentRepository).findByStudentId(eq("42"));
        assertEquals("Status", actualPaymentStatusResult);
    }

    /**
     * Method under test: {@link PaymentServiceImpl#paymentStatus(String)}
     */
    @Test
    void testPaymentStatus2() {
        // Arrange
        when(paymentRepository.findByStudentId(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> paymentServiceImpl.paymentStatus("42"));
        verify(paymentRepository).findByStudentId(eq("42"));
    }
}
