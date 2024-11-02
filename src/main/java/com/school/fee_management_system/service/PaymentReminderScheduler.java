package com.school.fee_management_system.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PaymentReminderScheduler {
    @Autowired
    private JavaMailSender mailSender;
    public void sendReminderEmail(String email,String name,double dueAmount) {
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Payment Reminder");
            message.setText("Dear " + name + ",\n\nYour payment of " + dueAmount +" is pending. Please make sure to pay on time to avoid any late fees.\n\nThank you!");
            JavaMailSender m = new JavaMailSenderImpl();
            m.send(message);

    }
}
