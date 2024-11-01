package com.school.fee_management_system.service;

import com.school.fee_management_system.model.*;
import com.school.fee_management_system.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeeCollectionServiceImplTest {

    @InjectMocks
    private FeeCollectionServiceImpl feeCollectionService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private CatalogRepository catalogRepository;

    @Mock
    private SequenceService sequenceService;

    private Student student;
    private Catalog catalog;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setId("student1");

        catalog = new Catalog();
        catalog.setId("catalog1");
        catalog.setName("Tuition Fee");
        catalog.setFee(1000.0);
    }

    @Test
    public void testCollectFee_Success() {
        when(studentRepository.findById("student1")).thenReturn(Optional.of(student));
        when(catalogRepository.findById("catalog1")).thenReturn(Optional.of(catalog));
        when(sequenceService.getNextSequence("payment")).thenReturn(1L);
        when(sequenceService.getNextSequence("receipt")).thenReturn(1L);

        Receipt receipt = feeCollectionService.collectFee("student1", "catalog1");

        assertNotNull(receipt);
        assertEquals("receipt1", receipt.getOrderId());
        assertEquals("payment1", receipt.getPaymentId());
        assertEquals("Tuition Fee", receipt.getReceiptType());
        assertEquals(1000.0, receipt.getAmount());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    public void testCollectFee_StudentNotFound() {
        when(studentRepository.findById("student1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            feeCollectionService.collectFee("student1", "catalog1");
        });

        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    public void testCollectFee_CatalogNotFound() {
        when(studentRepository.findById("student1")).thenReturn(Optional.of(student));
        when(catalogRepository.findById("catalog1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            feeCollectionService.collectFee("student1", "catalog1");
        });

        assertEquals("Catalog not found", exception.getMessage());
    }

    @Test
    public void testCollectFee_PaymentSaveFailure() {
        when(studentRepository.findById("student1")).thenReturn(Optional.of(student));
        when(catalogRepository.findById("catalog1")).thenReturn(Optional.of(catalog));
        when(sequenceService.getNextSequence("payment")).thenReturn(1L);
        doThrow(new RuntimeException("Payment save failed")).when(paymentRepository).save(any(Payment.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            feeCollectionService.collectFee("student1", "catalog1");
        });

        assertEquals("Payment save failed", exception.getMessage());
    }

    @Test
    public void testCollectFee_ReceiptSaveFailure() {
        when(studentRepository.findById("student1")).thenReturn(Optional.of(student));
        when(catalogRepository.findById("catalog1")).thenReturn(Optional.of(catalog));
        when(sequenceService.getNextSequence("payment")).thenReturn(1L);
        when(sequenceService.getNextSequence("receipt")).thenReturn(1L);
        doThrow(new RuntimeException("Receipt save failed")).when(receiptRepository).save(any(Receipt.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            feeCollectionService.collectFee("student1", "catalog1");
        });

        assertEquals("Receipt save failed", exception.getMessage());
    }
}
