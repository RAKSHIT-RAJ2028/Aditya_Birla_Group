import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../../services/auth';
import './Login.css'; // You'll need to create this CSS file

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  const [marketingConsent, setMarketingConsent] = useState(true);
  const navigate = useNavigate();
  const videoRef = useRef(null);

  const handleOAuthLogin = (provider) => {
  window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`;
}

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const userData = await authService.login(email, password);
      if (userData) {
        const user = authService.getCurrentUser();
        if (user.role === 'ROLE_ADMIN') {
          navigate('/admin-dashboard');
        } else {
          navigate('/user-dashboard');
        }
      }
    } catch (err) {
      setError('Login failed. Please check your credentials.');
      console.error(err);
    }
  };

  return (
    <div className="login-page">
      {/* Video Background Section */}
      <div className="video-section">
        <video 
          ref={videoRef}
          autoPlay 
          muted 
          loop 
          playsInline
          className="background-video"
        >
          <source src="/videos/business-background.mp4" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
        <div className="video-overlay">
          <h1 className="brand-title">ADITYA BIRLA GROUP</h1>
          <p className="brand-tagline">A Force For Good.</p>
        </div>
      </div>

      {/* Login Form Section */}
      <div className="form-section">
        <div className="login-container">
        <div className="login-header">
          <h2>Join or log in</h2>

          <div className="social-logins">
            <button type="button" className="social-btn">
              <img src="/images/social/facebook.png" alt="Facebook Login" className="social-logo" />
            </button>
            <button type="button" className="social-btn" onClick={() => window.location.href = 'http://localhost:8080/oauth2/authorization/google'}>
              <img src="/images/social/google.png" alt="Google Login" className="social-logo" />
            </button>
            <button type="button" className="social-btn">
              <img src="/images/social/twitter.png" alt="Twitter Login" className="social-logo" />
            </button>
            <button type="button" className="social-btn">
              <img src="/images/social/whatsapp.png" alt="WhatsApp Login" className="social-logo" />
            </button>
             <button type="button" className="social-btn" onClick={() => window.location.href = 'http://localhost:8080/oauth2/authorization/github'}>
              <img src="/images/social/github.png" alt="GitHub Login" className="social-logo" />
            </button>
          </div>
          <div className="divider">
            <span className="divider-line"></span>
            <span className="divider-text">or</span>
            <span className="divider-line"></span>
          </div>
          
          {error && <div className="alert alert-danger">{error}</div>}
          
          <form onSubmit={handleLogin} className="login-form">
            <div className="form-group">
              <input
                type="email"
                className="form-control"
                placeholder="Email address"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            
            <div className="form-group">
              <input
                type="password"
                className="form-control"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            
            <div className="form-options">
              <label className="checkbox-container">
                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={(e) => setRememberMe(e.target.checked)}
                />
                <span className="checkmark"></span>
                Remember me
              </label>
              
              <a href="/forgot-password" className="forgot-password">Forgot password?</a>
            </div>
            
            <div className="form-group marketing-consent">
              <label className="checkbox-container">
                <input
                  type="checkbox"
                  checked={marketingConsent}
                  onChange={(e) => setMarketingConsent(e.target.checked)}
                />
                <span className="checkmark"></span>
                Receive announcement and marketing letters.
              </label>
            </div>
            
            <button type="submit" className="btn btn-login">Log in</button>
            
            <div className="form-footer">
              <span>Don't have an account? <a href="/register">Sign up</a></span>
              <div className="footer-links">
                <a href="/help">Help</a>
                <a href="/privacy-policy">Privacy Policy</a>
                <a href="/phone-login">Phone login</a>
              </div>
            </div>
          </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;