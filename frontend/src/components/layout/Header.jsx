import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

function Header({ username  }) {
  return (
    <header className="app-header">
      <div className="header-container">
        <div className="logo-section">
          <Link to="/" className="logo-link">
            <img 
              src="/logos/hindalco_logo.jpg" 
              alt="Aditya Birla Group Textiles" 
              className="company-logo"
            />
          </Link>
          <div className="company-name">Aditya Birla Group</div>
        </div>
        <div className="user-controls">
          <span className="welcome-msg">Welcome, <strong>{username}</strong></span>
          <button className="notification-btn">
            <i className="icon-bell"></i>
            <span className="badge">3</span>
          </button>
          <div className="user-dropdown">
            <div className="user-avatar">
              <img src="/images/social/Raja_img.jpg" alt="User Profile" />
            </div>
            <div className="dropdown-content">
              <Link to="/profile">Profile</Link>
              <Link to="/settings">Settings</Link>
              <Link to="/logout" className="logout-btn">Logout</Link>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;