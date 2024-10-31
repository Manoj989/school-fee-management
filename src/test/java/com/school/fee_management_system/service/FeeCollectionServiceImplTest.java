package com.school.fee_management_system.service;

import com.school.fee_management_system.model.*;
import com.school.fee_management_system.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeeCollectionServiceImplTest {

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

    @InjectMocks
    private FeeCollectionServiceImpl feeCollectionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCollectFee() {
        String studentId = "student1";
        String catalogId = "catalog1";

        Student student = new Student();
        student.setId(studentId);

        Catalog catalog = new Catalog();
        catalog.setId(catalogId);
        catalog.setFee(1000.0);
        catalog.setName("Tuition");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(sequenceService.getNextSequence("payment")).thenReturn(1L);
        when(sequenceService.getNextSequence("receipt")).thenReturn(1L);

        Receipt receipt = feeCollectionService.collectFee(studentId, catalogId);

        assertNotNull(receipt);
        assertEquals("receipt1", receipt.getOrderId());
        assertEquals("payment1", receipt.getPaymentId());
        assertEquals("Tuition", receipt.getReceiptType());
        assertNotNull(receipt.getDate());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    public void testCollectFeeWithDifferentCatalog() {
        String studentId = "student1";
        String catalogId = "catalog2";

        Student student = new Student();
        student.setId(studentId);

        Catalog catalog = new Catalog();
        catalog.setId(catalogId);
        catalog.setFee(2000.0);
        catalog.setName("Library");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(sequenceService.getNextSequence("payment")).thenReturn(2L);
        when(sequenceService.getNextSequence("receipt")).thenReturn(2L);

        Receipt receipt = feeCollectionService.collectFee(studentId, catalogId);

        assertNotNull(receipt);
        assertEquals("receipt2", receipt.getOrderId());
        assertEquals("payment2", receipt.getPaymentId());
        assertEquals("Library", receipt.getReceiptType());
        assertNotNull(receipt.getDate());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    public void testCollectFeeWithDifferentStudent() {
        String studentId = "student2";
        String catalogId = "catalog1";

        Student student = new Student();
        student.setId(studentId);

        Catalog catalog = new Catalog();
        catalog.setId(catalogId);
        catalog.setFee(1500.0);
        catalog.setName("Sports");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(sequenceService.getNextSequence("payment")).thenReturn(3L);
        when(sequenceService.getNextSequence("receipt")).thenReturn(3L);

        Receipt receipt = feeCollectionService.collectFee(studentId, catalogId);

        assertNotNull(receipt);
        assertEquals("receipt3", receipt.getOrderId());
        assertEquals("payment3", receipt.getPaymentId());
        assertEquals("Sports", receipt.getReceiptType());
        assertNotNull(receipt.getDate());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(receiptRepository, times(1)).save(any(Receipt.class));
    }

    @Test
    public void testCollectFeeStudentNotFound() {
        String studentId = "student1";
        String catalogId = "catalog1";

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            feeCollectionService.collectFee(studentId, catalogId);
        });

        assertEquals("Student not found", exception.getMessage());

        verify(paymentRepository, never()).save(any(Payment.class));
        verify(receiptRepository, never()).save(any(Receipt.class));
    }

    @Test
    public void testCollectFeeCatalogNotFound() {
        String studentId = "student1";
        String catalogId = "catalog1";

        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(catalogRepository.findById(catalogId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            feeCollectionService.collectFee(studentId, catalogId);
        });

        assertEquals("Catalog not found", exception.getMessage());

        verify(paymentRepository, never()).save(any(Payment.class));
        verify(receiptRepository, never()).save(any(Receipt.class));
    }
}
