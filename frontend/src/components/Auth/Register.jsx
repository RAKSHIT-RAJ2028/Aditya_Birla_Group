import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

const Register = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    mobile: '',
    password: '',
    confirmPassword: '',
    acceptTerms: false,
    marketingConsent: true
  });
  const [verificationMethod, setVerificationMethod] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Sample logos - replace with your actual logo imports
  const logos = [
    { id: 1, name: 'PT ELEGANT', logo: '/logos/pt-elegant.jpg' },
    { id: 2, name: 'GRASIM', logo: '/logos/Grasim_logo_Aw.jpg' },
    { id: 3, name: 'HINDALCO', logo: '/logos/hindalco_logo.jpg' },
    { id: 4, name: 'CHEMICALS', logo: '/logos/3d-chemicals.jpg' },
    { id: 5, name: 'BIRLA CARBON', logo: '/logos/3d-birla-carbon.jpg' },
    { id: 6, name: 'ADITYA BIRLA CAPITAL', logo: '/logos/aditya-birla-capital.jpg' },
    { id: 7, name: 'NOVLIS', logo: '/logos/1390_ABG_Novelis_logo_Aw.jpg' },
    { id: 8, name: 'BIRLA INSULATOR', logo: '/logos/3d-insulators.jpg' }
    
  ];

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError('');
    
    // Basic validation
    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }
    
    if (!formData.acceptTerms) {
      setError('You must accept the terms and conditions');
      return;
    }
    
    // Proceed to verification method selection
    setStep(2);
  };

  const handleVerificationMethod = (method) => {
    setVerificationMethod(method);
    setStep(3); // Move to OTP verification
  };

  return (
    <div className="register-page">
      {/* Logos Section */}
      <div className="logos-section">
        <div className="logos-container">
          <h2>Our Brands</h2>
          <div className="logo-grid">
            {logos.slice(0, 4).map(logo => (
              <div key={logo.id} className="logo-item">
                <img src={logo.logo} alt={logo.name} />
                <span>{logo.name}</span>
              </div>
            ))}
          </div>
          <div className="logo-grid">
            {logos.slice(4, 8).map(logo => (
              <div key={logo.id} className="logo-item">
                <img src={logo.logo} alt={logo.name} />
                <span>{logo.name}</span>
              </div>
            ))}
          </div>
          
        </div>
      </div>

      {/* Form Section */}
      <div className="form-section">
        <div className="register-container">
          <h2>Create your account</h2>
          {error && <div className="alert alert-danger">{error}</div>}

          {step === 1 && (
            <form onSubmit={handleSubmit} className="register-form">
              <div className="form-row">
                <div className="form-group">
                  <label>First Name</label>
                  <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    required
                  />
                </div>
                <div className="form-group">
                  <label>Last Name</label>
                  <input
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

              <div className="form-group">
                <label>Email Address</label>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Mobile Number</label>
                <input
                  type="tel"
                  name="mobile"
                  value={formData.mobile}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Password</label>
                <input
                  type="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Confirm Password</label>
                <input
                  type="password"
                  name="confirmPassword"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-checkbox">
                <label>
                  <input
                    type="checkbox"
                    name="acceptTerms"
                    checked={formData.acceptTerms}
                    onChange={handleChange}
                    required
                  />
                  I accept the <a href="/terms">Terms and Conditions</a>
                </label>
              </div>

              <div className="form-checkbox">
                <label>
                  <input
                    type="checkbox"
                    name="marketingConsent"
                    checked={formData.marketingConsent}
                    onChange={handleChange}
                  />
                  Receive announcements and marketing letters
                </label>
              </div>

              <button type="submit" className="btn btn-primary">
                Continue
              </button>
            </form>
          )}

          {step === 2 && (
            <div className="verification-method">
              <h3>Verify your account</h3>
              <p>Choose how you'd like to verify your account:</p>
              
              <div className="verification-options">
                <button 
                  className="verification-btn email-btn"
                  onClick={() => handleVerificationMethod('email')}
                >
                  <i className="icon-email"></i>
                  <span>Email OTP to {formData.email}</span>
                </button>
                
                <button 
                  className="verification-btn sms-btn"
                  onClick={() => handleVerificationMethod('sms')}
                >
                  <i className="icon-phone"></i>
                  <span>SMS OTP to +91 •••• ••{formData.mobile.slice(-4)}</span>
                </button>
              </div>
              
              <button 
                className="btn btn-back"
                onClick={() => setStep(1)}
              >
                Back
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Register;