package com.busbooking.services;

import com.busbooking.model.Booking;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender; // For sending emails

    @Autowired
    private SmsService smsService; // For sending SMS

    @Async
    public void sendBookingNotifications(Booking booking) {
        sendBookingEmail(booking);
        sendBookingSms(booking);
    }

    private void sendBookingEmail(Booking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(booking.getUser().getEmail());
            helper.setSubject("Booking Confirmation - " + booking.getSeatNumbers());
            helper.setText("Dear " + booking.getUser().getUsername() + ",\n\n" +
                    "Your seat " + booking.getSeatNumbers() + " on bus " +
                    booking.getBus().getBusId() + " is confirmed.\n\n" +
                    "Total Fare: $" + booking.getTotalFare() + "\n\n" +
                    "Thank you for booking with us!");

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendBookingSms(Booking booking) {
        String phoneNumber = booking.getUser().getPhone();
        String message = "Booking confirmed! Seat: " + booking.getSeatNumbers() +
                ", Bus: " + booking.getBus().getBusId() +
                ", Fare: ₹" + booking.getTotalFare();

        smsService.sendSms(phoneNumber, message);
    }
}
