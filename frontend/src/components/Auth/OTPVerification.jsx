import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './Register.css';

const OTPVerification = () => {
  const [otp, setOtp] = useState(['', '', '', '', '', '']);
  const [error, setError] = useState('');
  const [isResending, setIsResending] = useState(false);
  const [countdown, setCountdown] = useState(30);
  const navigate = useNavigate();
  const location = useLocation();

  // Get verification method from location state
  const verificationMethod = location.state?.method || 'email';

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

  const handleVerify = (e) => {
    e.preventDefault();
    const otpCode = otp.join('');
    
    if (otpCode.length !== 6) {
      setError('Please enter the complete 6-digit OTP');
      return;
    }

    // Here you would typically verify the OTP with your backend
    console.log(`Verifying ${verificationMethod} OTP:`, otpCode);
    
    // On successful verification:
    navigate('/registration-success');
  };

  const handleResendOtp = () => {
    setIsResending(true);
    setError('');
    
    // Here you would typically call your backend to resend OTP
    console.log(`Resending ${verificationMethod} OTP`);
    
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
  };

  return (
    <div className="otp-page">
      <div className="otp-container">
        <h2>Verify your {verificationMethod === 'email' ? 'Email' : 'Mobile'}</h2>
        <p className="otp-instructions">
          We've sent a 6-digit verification code to your{' '}
          {verificationMethod === 'email' ? 'email address' : 'mobile number'}
        </p>
        
        {error && <div className="alert alert-danger">{error}</div>}
        
        <form onSubmit={handleVerify} className="otp-form">
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
          
          <button type="submit" className="btn btn-verify">
            Verify & Continue
          </button>
          
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
          
          <button
            type="button"
            className="btn btn-back"
            onClick={() => navigate(-1)}
          >
            Back
          </button>
        </form>
      </div>
    </div>
  );
};

export default OTPVerification;