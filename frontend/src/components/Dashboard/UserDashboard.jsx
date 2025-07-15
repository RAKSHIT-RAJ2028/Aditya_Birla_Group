import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from '../layout/Header';
import Footer from '../layout/Footer';
import PrimaryNav from '../layout/PrimaryNav';
import SecondaryNav from '../layout/SecondaryNav';
import authService from '../../services/auth';
import './UserDashboard.css';

function UserDashboard() {
  const currentUser = authService.getCurrentUser();

  return (
    <div className="app-container">
      <Header />
      <div className="main-content">
        <PrimaryNav />
        <div className="content-area">
          <SecondaryNav />
          <div className="dashboard-content">
            <div className="dashboard-header">
              <h1>Textile Management Dashboard</h1>
              <p>Welcome, {currentUser.username}</p>
            </div>
            
            <div className="dashboard-grid">
              {/* Material Quick Access */}
              <div className="card quick-access">
                <h3>Quick Actions</h3>
                <div className="quick-actions">
                  <button className="action-btn">
                    <i className="icon-search"></i>
                    <span>Find Material</span>
                  </button>
                  <button className="action-btn">
                    <i className="icon-report"></i>
                    <span>Generate Report</span>
                  </button>
                </div>
              </div>
              
              {/* My Recent Activities */}
              <div className="card activities-card">
                <h3>My Recent Activities</h3>
                <ul className="activities-list">
                  <li>
                    <span className="activity">Updated Cotton inventory (+500kg)</span>
                    <span className="timestamp">2 hours ago</span>
                  </li>
                  <li>
                    <span className="activity">Added new supplier "Silk Road Imports"</span>
                    <span className="timestamp">Yesterday</span>
                  </li>
                </ul>
              </div>
              
              {/* Material Highlights */}
              <div className="card highlights-card">
                <h3>Material Highlights</h3>
                <div className="material-highlights">
                  <div className="highlight-item">
                    <div className="highlight-color" style={{backgroundColor: '#4CAF50'}}></div>
                    <div className="highlight-info">
                      <h4>Organic Cotton</h4>
                      <p>Current stock: 1,250kg</p>
                    </div>
                  </div>
                  {/* More highlight items... */}
                </div>
              </div>
            </div>
            
            <Outlet /> {/* For nested routes */}
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
}

export default UserDashboard;