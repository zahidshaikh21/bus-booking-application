package com.busbooking.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    public void sendSms(String phoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                            new PhoneNumber(phoneNumber),   // To
                            new PhoneNumber(twilioPhoneNumber), // From Twilio Number
                            messageBody)
                    .create();

            System.out.println("SMS Sent Successfully! SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }
}
