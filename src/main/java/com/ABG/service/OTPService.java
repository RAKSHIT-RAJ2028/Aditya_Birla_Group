package com.ABG.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {
    private final Map<String, OTPData> otpMap = new HashMap<>();
    
    public String generateOTP(String identifier) {
        String otp = RandomStringUtils.randomNumeric(6);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5); // 5 minutes expiration
        
        otpMap.put(identifier, new OTPData(otp, expirationTime));
        return otp;
    }
    
    public boolean validateOTP(String identifier, String otp) {
        OTPData otpData = otpMap.get(identifier);
        if (otpData == null) {
            return false;
        }
        
        if (LocalDateTime.now().isAfter(otpData.getExpirationTime())) {
            otpMap.remove(identifier);
            return false;
        }
        
        boolean isValid = otpData.getOtp().equals(otp);
        if (isValid) {
            otpMap.remove(identifier);
        }
        return isValid;
    }
    
    private static class OTPData {
        private final String otp;
        private final LocalDateTime expirationTime;
        
        public OTPData(String otp, LocalDateTime expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }
        
        public String getOtp() { return otp; }
        public LocalDateTime getExpirationTime() { return expirationTime; }
    }
}