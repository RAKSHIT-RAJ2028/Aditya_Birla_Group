package com.ABG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ABG.service.EmailService;
import com.ABG.service.OTPService;
import com.ABG.service.SMSService;

@RestController
@RequestMapping("/auth")
public class VerificationController {
    @Autowired
    private OTPService otpService;
    
    @Autowired
    private SMSService smsService;
    
    @Autowired
    private EmailService emailService;
    
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestParam String method, 
                                    @RequestParam String identifier) {
        String otp = otpService.generateOTP(identifier);
        
        if ("email".equalsIgnoreCase(method)) {
            emailService.sendOTP(identifier, otp);
        } else if ("sms".equalsIgnoreCase(method)) {
            smsService.sendOTP(identifier, otp);
        } else {
            return ResponseEntity.badRequest().body("Invalid verification method");
        }
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestParam String identifier, 
                                      @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(identifier, otp);
        
        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
}