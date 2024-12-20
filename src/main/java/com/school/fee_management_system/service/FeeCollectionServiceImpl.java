package com.school.fee_management_system.service;

import com.school.fee_management_system.Response.UnpaidStudentsResponse;
import com.school.fee_management_system.model.*;
import com.school.fee_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class FeeCollectionServiceImpl implements FeeCollectionService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private PaymentReminderScheduler paymentReminderScheduler;






    @Transactional
    @Override
    public Receipt collectFee(String studentId, String catalogId, double amountPaid, PaymentPlan paymentPlan) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if(!student.getCatalogId().equals(catalogId)) {
            throw new RuntimeException("Catalog id mismatch. Either update catalog id for student or provide correct catalog id");
        }
        Catalog catalog = catalogRepository.findById(student.getCatalogId())
                .orElseThrow(() -> new RuntimeException("Catalog not found"));

        List<Payment> payments=paymentRepository.findByStudentIdAndCatalogId(studentId,catalogId);
        Payment payment;
        if(!payments.isEmpty()){
            payment = payments.get(0);
        }
        else{
            payment=createNewPayment(studentId, catalog, paymentPlan);
        }

        if(!payment.getStatus().equals("PAID")){
            updatePayment(payment, amountPaid, paymentPlan);

            Receipt receipt = createReceipt(payment, String.valueOf(paymentPlan),amountPaid);
            receiptRepository.save(receipt);

            return receipt;
        }
        else{
            Receipt receipt = new Receipt();
            receipt.setMessage("All dues are Paid. No dues remaining");
            return receipt;
        }


    }

    private Payment createNewPayment(String studentId, Catalog catalog, PaymentPlan paymentPlan) {
        Payment payment = new Payment();
        long nextPaymentId = sequenceService.getNextSequence("payment");
        payment.setId("payment"+nextPaymentId);
        payment.setStudentId(studentId);
        payment.setCatalogId(catalog.getId());
        payment.setOriginalAmount(catalog.getFee());
        payment.setDueDate(java.sql.Date.valueOf(calculateDueDate(paymentPlan)));
        payment.setStatus("PENDING");
        return payment;
    }

    private void updatePayment(Payment payment, double amountPaid, PaymentPlan paymentPlan) {
        if(payment.getOriginalAmount() != amountPaid && paymentPlan.equals(PaymentPlan.ANNUAL)) {
            throw new RuntimeException("Since Payment Type is Annual Part Payment not allowed");
        }
        if(amountPaid > payment.getOriginalAmount()){
            throw new RuntimeException("Amount paid can not be greater than original amount");
        }
        double discount = calculateDiscount(payment.getOriginalAmount(), paymentPlan);
        if(paymentPlan.equals(PaymentPlan.ANNUAL)){
            payment.setAmountDue(discount-amountPaid);
            payment.setPaidAmount(amountPaid);
            payment.setStatus("PAID");
            payment.setPaymentDate(new Date());
        }
        if(paymentPlan.equals(PaymentPlan.HALF_YEARLY)){
            if((amountPaid ==discount/2)){
                payment.setAmountDue(discount-amountPaid-payment.getPaidAmount());
                payment.setPaidAmount(amountPaid);
                payment.setStatus("PARTIAL");
                if(payment.getAmountDue()==0){
                    payment.setStatus("PAID");
                }
                payment.setPaymentDate(new Date());
            }else{
                throw new RuntimeException("Payment Type is Half Yearly, 10% discounted fee is applicable. Half of discounted fee to be paid:"+discount/2);
            }
        }
        else if(paymentPlan.equals(PaymentPlan.QUARTERLY)){
            if((amountPaid == discount/4)){
                payment.setAmountDue(discount-amountPaid-payment.getPaidAmount());
                payment.setPaidAmount(amountPaid);
                payment.setStatus("PARTIAL");
                if(payment.getAmountDue()==0){
                    payment.setStatus("PAID");
                }
                payment.setPaymentDate(new Date());
            }else{
                throw new RuntimeException("Payment Type is Quarterly, 5% discounted fee is applicable. 1/4 discounted fee to be paid:"+ discount/4);
            }
        }else{
            if (new Date().after(payment.getDueDate())) {
                double daysLate = (double) (new Date().getTime() - payment.getDueDate().getTime()) / (1000 * 60 * 60 * 24);
                payment.setFine(daysLate * 1000);
            }
            payment.setAmountDue((payment.getFine()+payment.getOriginalAmount())-(amountPaid+payment.getPaidAmount()));
            payment.setPaidAmount(amountPaid);
            payment.setStatus("PARTIAL");
            if(payment.getAmountDue()==0){
                payment.setStatus("PAID");
            }
            payment.setPaymentDate(new Date());
        }
        paymentRepository.save(payment);
    }

    private Receipt createReceipt(Payment payment,String paymentPlan, double amountPaid) {
        Receipt receipt = new Receipt();
        long nextReceiptId = sequenceService.getNextSequence("receipt");
        receipt.setId("receipt" + nextReceiptId);
        receipt.setPaymentId(payment.getId());
        receipt.setDate(new Date());
        receipt.setAmount(amountPaid);
        receipt.setReceiptType(paymentPlan);
        receipt.setMessage("Fees Collection Success");
        return receipt;
    }

    private LocalDate calculateDueDate(PaymentPlan paymentPlan) {
        LocalDate today = LocalDate.now();
        return switch (paymentPlan) {
            case ANNUAL -> today.plusYears(1);
            case HALF_YEARLY -> today.plusMonths(6);
            case QUARTERLY -> today.plusMonths(3);
            default -> today.withDayOfMonth(today.lengthOfMonth());
        };
    }

    private double calculateDiscount(double amount, PaymentPlan paymentPlan) {
        return switch (paymentPlan) {
            case ANNUAL -> amount - (amount * 0.15); // 15% discount
            case QUARTERLY -> amount - (amount * 0.05); // 5% discount
            case HALF_YEARLY -> amount - (amount * 0.10); // 10% discount
            default -> 0;
        };
    }
    @Override
    public double getTotalFeesCollectedBetween(Date startDate, Date endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate).stream()
                .mapToDouble(Payment::getPaidAmount)
                .sum();
    }

    @Override
    public List<UnpaidStudentsResponse> getUnpaidPayments() {
        Date currentDate = new Date();
        List<String> studentIds = paymentRepository.findAll().stream()
                .filter(payment -> payment.getDueDate().after(currentDate))
                .map(Payment::getStudentId)
                .distinct()
                .toList();
        List<Student> allStudents=studentRepository.findAll();
        allStudents.removeIf(student -> studentIds.contains(student.getId()));

        List<UnpaidStudentsResponse> list = new ArrayList<>();
        for(Student s:allStudents){
           Optional<Catalog> c= catalogRepository.findById(s.getCatalogId());
           if (c.isPresent()){
               UnpaidStudentsResponse unpaidStudentsResponse = new UnpaidStudentsResponse();
               unpaidStudentsResponse.setStudentId(s.getId());
               unpaidStudentsResponse.setStudentName(s.getName());
               unpaidStudentsResponse.setCourseName(c.get().getName());
               unpaidStudentsResponse.setDueAmount(c.get().getFee());
               unpaidStudentsResponse.setStudentEmail(s.getEmail());
               list.add(unpaidStudentsResponse);
           }
        }
        return list;
    }

    @Override
    public double getDueAmountForStudent(String studentId) {
        List<Payment> paymentsPending = paymentRepository.findByStudentIdAndStatus(studentId, "PENDING");
        List<Payment> paymentsPartial = paymentRepository.findByStudentIdAndStatus(studentId, "PARTIAL");
        List<Payment> payments= new ArrayList<>();
        payments.addAll(paymentsPending);
        payments.addAll(paymentsPartial);
        if(payments.isEmpty()){
            Optional<Student> student=studentRepository.findById(studentId);
            if(student.isPresent()){
                Optional<Catalog> catalog=catalogRepository.findById(student.get().getCatalogId());
                if(catalog.isPresent()){
                    return catalog.get().getFee();
                }
            }else{
                throw new RuntimeException("Student not found");
            }

        }
        return payments.stream()
                .mapToDouble(Payment::getAmountDue)
                .sum();
    }

    public void sendReminders()
    {
        List<UnpaidStudentsResponse> list=this.getUnpaidPayments();
        for(UnpaidStudentsResponse unpaidStudentsResponse:list){
            paymentReminderScheduler.sendReminderEmail(unpaidStudentsResponse.getStudentEmail(),unpaidStudentsResponse.getStudentName(),unpaidStudentsResponse.getDueAmount());
        }

    }
}




