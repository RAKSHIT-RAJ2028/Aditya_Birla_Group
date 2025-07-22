import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Auth/Login';
import Register from './components/Auth/Register';
import AdminDashboard from './components/Dashboard/AdminDashboard';
import UserDashboard from './components/Dashboard/UserDashboard';
import authService from './services/auth';
import OAuth2RedirectHandler from './components/Auth/OAuth2RedirectHandler';
import VerifyOTP from './components/Auth/VerifyOTP';


function App() {
  const PrivateRoute = ({ children, requiredRole }) => {
    const currentUser = authService.getCurrentUser();
    
    if (!currentUser) {
      return <Navigate to="/login" replace />;
    }
    
    if (requiredRole && currentUser.role !== requiredRole) {
      return <Navigate to="/" replace />;
    }
    
    return children;
  };

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/oauth2-redirect" element={<OAuth2RedirectHandler />} />
        <Route path="/verify-otp" element={<VerifyOTP />} />

        <Route 
          path="/admin-dashboard" 
          element={
            <PrivateRoute requiredRole="ROLE_ADMIN">
              <AdminDashboard />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/user-dashboard" 
          element={
            <PrivateRoute requiredRole="ROLE_USER">
              <UserDashboard />
            </PrivateRoute>
          } 
        />
        <Route path="/" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

export default App;