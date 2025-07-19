import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './VerifyOTP.css'; // Create this CSS file for styling

const VerifyOTP = () => {
  const [otp, setOtp] = useState(['', '', '', '', '', '']);
  const [error, setError] = useState('');
  const [isResending, setIsResending] = useState(false);
  const [countdown, setCountdown] = useState(30);
  const navigate = useNavigate();
  const location = useLocation();

  // Get email/mobile from location state
  const identifier = location.state?.identifier || '';
  const method = location.state?.method || 'email';

  const handleOtpChange = (e, index) => {
    const value = e.target.value;
    if (isNaN(value)) return;

    const newOtp = [...otp];
    newOtp[index] = value;
    setOtp(newOtp);

    // Auto focus to next input
    if (value && index < 5) {
      document.getElementById(`otp-${index + 1}`).focus();
    }
  };

  const handleVerify = async (e) => {
    e.preventDefault();
    const otpCode = otp.join('');
    
    if (otpCode.length !== 6) {
      setError('Please enter the complete 6-digit OTP');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/auth/verify-otp', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `identifier=${encodeURIComponent(identifier)}&otp=${otpCode}`
      });

      if (!response.ok) {
        throw new Error('Invalid or expired OTP');
      }

      // On successful verification, redirect to success page
      navigate('/user-dashboard', { 
        state: { 
          email: identifier,
          verified: true
        }
      });
    } catch (error) {
      setError(error.message);
    }
  };

  const handleResendOtp = async () => {
    setIsResending(true);
    setError('');
    
    try {
      const response = await fetch('http://localhost:8080/auth/send-otp', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `method=${method}&identifier=${encodeURIComponent(identifier)}`
      });

      if (!response.ok) {
        throw new Error('Failed to resend OTP');
      }

      // Start countdown
      setCountdown(30);
      const timer = setInterval(() => {
        setCountdown(prev => {
          if (prev <= 1) {
            clearInterval(timer);
            setIsResending(false);
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
    } catch (error) {
      setError(error.message);
      setIsResending(false);
    }
  };

  return (
    <div className="otp-verification-container">
      <h2>Verify Your {method === 'email' ? 'Email' : 'Phone'}</h2>
      <p className="otp-instructions">
        We've sent a 6-digit verification code to your {method === 'email' ? 'email address' : 'phone number'}
        <br />
        <strong>{identifier}</strong>
      </p>
      
      {error && <div className="error-message">{error}</div>}
      
      <form onSubmit={handleVerify}>
        <div className="otp-inputs">
          {otp.map((digit, index) => (
            <input
              key={index}
              id={`otp-${index}`}
              type="text"
              maxLength="1"
              value={digit}
              onChange={(e) => handleOtpChange(e, index)}
              onFocus={(e) => e.target.select()}
              required
            />
          ))}
        </div>
        
        <button type="submit" className="verify-button">
          Verify & Continue
        </button>
      </form>
      
      <div className="resend-otp">
        <p>Didn't receive the code?</p>
        <button
          type="button"
          onClick={handleResendOtp}
          disabled={isResending || countdown > 0}
          className={isResending || countdown > 0 ? 'disabled' : ''}
        >
          {countdown > 0 ? `Resend in ${countdown}s` : 'Resend OTP'}
        </button>
      </div>
    </div>
  );
};

export default VerifyOTP;