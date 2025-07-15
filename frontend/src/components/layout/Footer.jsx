import React from 'react';
import './Footer.css';

function Footer() {
  return (
    <footer className="app-footer">
      <div className="footer-container">
        <div className="footer-section">
          <h4>Aditya Birla Textiles</h4>
          <p>Innovating the fabric of tomorrow</p>
        </div>
        <div className="footer-section">
          <p>&copy; {new Date().getFullYear()} Aditya Birla Group</p>
          <p>All rights reserved</p>
        </div>
        <div className="footer-section">
          <a href="/privacy">Privacy Policy</a>
          <a href="/terms">Terms of Service</a>
        </div>
      </div>
    </footer>
  );
}

export default Footer;