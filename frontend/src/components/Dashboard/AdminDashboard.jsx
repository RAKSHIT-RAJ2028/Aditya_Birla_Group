import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from '../layout/Header';
import Footer from '../layout/Footer';
import PrimaryNav from '../layout/PrimaryNav';
import SecondaryNav from '../layout/SecondaryNav';
import authService from '../../services/auth';
import './AdminDashboard.css';

function AdminDashboard() {
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
              <h1>Admin Dashboard</h1>
              <p>Welcome, {currentUser.username} (Admin)</p>
            </div>
            
            <div className="dashboard-grid">
              {/* Quick Stats */}
              <div className="card stats-card">
                <h3>Materials Summary</h3>
                <div className="stats-grid">
                  <div className="stat-item">
                    <span className="stat-value">142</span>
                    <span className="stat-label">Total Materials</span>
                  </div>
                  <div className="stat-item">
                    <span className="stat-value">24</span>
                    <span className="stat-label">Low Stock</span>
                  </div>
                </div>
              </div>
              
              {/* Recent Materials */}
              <div className="card materials-card">
                <h3>Recent Materials</h3>
                <table className="materials-table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Type</th>
                      <th>Stock</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>TX-1001</td>
                      <td>Organic Cotton</td>
                      <td>Natural Fiber</td>
                      <td className="stock-ok">1,250 kg</td>
                      <td>
                        <button className="action-btn view">View</button>
                      </td>
                    </tr>
                    {/* More rows... */}
                  </tbody>
                </table>
              </div>
              
              {/* Inventory Alerts */}
              <div className="card alerts-card">
                <h3>Inventory Alerts</h3>
                <ul className="alerts-list">
                  <li className="alert-item critical">
                    <span>Polyester Blend - Red Alert (5kg left)</span>
                  </li>
                  <li className="alert-item warning">
                    <span>Silk Fabric - Low Stock (25kg left)</span>
                  </li>
                </ul>
              </div>
              
              {/* Employee Management */}
              <div className="card employees-card">
                <h3>Employee Management</h3>
                <div className="employee-actions">
                  <button className="btn primary">Add Employee</button>
                  <button className="btn secondary">Generate Salary Slips</button>
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

export default AdminDashboard;