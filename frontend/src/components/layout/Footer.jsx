import React from 'react';
import './Footer.css';

function Footer() {
  const currentYear = new Date().getFullYear();
  
  const socialLinks = [
    { icon: 'fa-brands fa-linkedin', url: "https://linkedin.com/company/aditya-birla" },
    { icon: 'fa-brands fa-twitter', url: "https://twitter.com/adityabirlagroup" },
    { icon: 'fa-brands fa-youtube', url: "https://youtube.com/adityabirlagroup" }
  ];

  const quickLinks = [
    { title: "About Us", url: "/about" },
    { title: "Sustainability", url: "/sustainability" },
    { title: "Investors", url: "/investors" },
    { title: "Careers", url: "/careers" },
    { title: "Newsroom", url: "/news" }
  ];

  const legalLinks = [
    { title: "Privacy Policy", url: "/privacy" },
    { title: "Terms of Service", url: "/terms" },
    { title: "Cookie Policy", url: "/cookies" },
    { title: "Accessibility", url: "/accessibility" }
  ];

  return (
    <footer className="app-footer">
      <div className="footer-top">
        <div className="footer-container">
          <div className="footer-brand">
            <div className="logo-container">
              <img 
                src="/logos/hindalco_logo.jpg" 
                alt="Aditya Birla Group" 
                className="footer-logo"
              />
            </div>
            <p className="brand-tagline">Innovating the fabric of tomorrow</p>
            <div className="social-links">
              {socialLinks.map((link, index) => (
                <a 
                  key={index} 
                  href={link.url} 
                  target="_blank" 
                  rel="noopener noreferrer"
                  aria-label={`Social media link ${index}`}
                >
                  <i className={link.icon}></i>
                </a>
              ))}
            </div>
          </div>

          <div className="footer-links">
            <h4 className="links-heading">Quick Links</h4>
            <ul>
              {quickLinks.map((link, index) => (
                <li key={index}>
                  <a href={link.url}>{link.title}</a>
                </li>
              ))}
            </ul>
          </div>

          <div className="footer-links">
            <h4 className="links-heading">Legal</h4>
            <ul>
              {legalLinks.map((link, index) => (
                <li key={index}>
                  <a href={link.url}>{link.title}</a>
                </li>
              ))}
            </ul>
          </div>

          <div className="footer-contact">
            <h4 className="links-heading">Contact Us</h4>
            <ul className="contact-info">
              <li>
                <i className="fas fa-map-marker-alt contact-icon"></i>
                <span>Aditya Birla Group, Worli, Mumbai 400030, India</span>
              </li>
              <li>
                <i className="fas fa-phone contact-icon"></i>
                <a href="tel:+912266665555">+91 22 6665 5555</a>
              </li>
              <li>
                <i className="fas fa-envelope contact-icon"></i>
                <a href="mailto:contact@adityabirla.com">contact@adityabirla.com</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div className="footer-bottom">
        <div className="footer-container">
          <p className="copyright">
            &copy; {currentYear} Aditya Birla Group. All rights reserved.
          </p>
          <div className="language-selector">
            <select aria-label="Language selector">
              <option value="en">English</option>
              <option value="hi">हिन्दी</option>
            </select>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;