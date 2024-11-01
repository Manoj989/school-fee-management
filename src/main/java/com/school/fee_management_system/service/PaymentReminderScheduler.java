package com.school.fee_management_system.service;

import com.school.fee_management_system.model.Payment;
import com.school.fee_management_system.model.Student;
import com.school.fee_management_system.repository.PaymentRepository;
import com.school.fee_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class PaymentReminderScheduler {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Scheduled(cron = "0 0 9 * * ?") // Runs every day at 9 AM
    public void sendPaymentReminders() {
        List<Payment> paymentsPending = paymentRepository.findByStatus("PENDING");
        List<Payment> paymentsPartial = paymentRepository.findByStatus("PARTIAL");
        List<Payment> payments = new ArrayList<>();
        payments.addAll(paymentsPartial);
        payments.addAll(paymentsPending);
        for (Payment payment : payments) {
            sendReminderEmail(payment);
        }
    }

    private void sendReminderEmail(Payment payment) {
        SimpleMailMessage message = new SimpleMailMessage();
        Optional<Student> student= studentRepository.findById(payment.getStudentId());
        if(student.isPresent()) {
            Student studentObject = student.get();
            message.setTo(studentObject.getEmail());
            message.setSubject("Payment Reminder");
            message.setText("Dear " + studentObject.getName() + ",\n\nYour payment of $" + payment.getAmountDue() + " is due on " + payment.getDueDate() + ". Please make sure to pay on time to avoid any late fees.\n\nThank you!");
            JavaMailSender m = new JavaMailSenderImpl();
            m.send(message);
        }
    }
}
