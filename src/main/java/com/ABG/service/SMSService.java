package com.ABG.service;

import com.twilio.Twilio;
//import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SMSService {
    @Value("${twilio.account.sid}")
    private String accountSid;
    
    @Value("${twilio.auth.token}")
    private String authToken;
    
    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;
    
    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
    
    public void sendOTP(String phoneNumber, String otp) {
        String message = "Your verification code is: " + otp + ". Valid for 5 minutes.";
        
        Message.creator(
            new PhoneNumber(phoneNumber),
            new PhoneNumber(twilioPhoneNumber),
            message
        ).create();
    }
}