import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

function Header() {
  return (
    <header className="app-header">
      <div className="header-container">
        <Link to="/" className="logo-link">
          <img 
            src="/logos/hindalco_logo.jpg" 
            alt="Aditya Birla Group Textiles" 
            className="company-logo"
          />
        </Link>
        <div className="user-controls">
          <span className="welcome-msg">Welcome, User</span>
          <button className="notification-btn">
            <i className="icon-bell"></i>
            <span className="badge">3</span>
          </button>
          <div className="user-avatar">
            <img src="/images/user-avatar.png" alt="User Profile" />
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;