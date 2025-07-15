import React from 'react';
import { NavLink } from 'react-router-dom';
import './PrimaryNav.css';

function PrimaryNav() {
  return (
    <nav className="primary-nav">
      <ul>
        <li>
          <NavLink to="/dashboard" activeClassName="active">
            <i className="icon-dashboard"></i>
            <span>Dashboard</span>
          </NavLink>
        </li>
        <li>
          <NavLink to="/materials" activeClassName="active">
            <i className="icon-fabric"></i>
            <span>Materials</span>
          </NavLink>
        </li>
        <li>
          <NavLink to="/inventory" activeClassName="active">
            <i className="icon-inventory"></i>
            <span>Inventory</span>
          </NavLink>
        </li>
        <li>
          <NavLink to="/suppliers" activeClassName="active">
            <i className="icon-suppliers"></i>
            <span>Suppliers</span>
          </NavLink>
        </li>
        <li>
          <NavLink to="/applications" activeClassName="active">
            <i className="icon-applications"></i>
            <span>Applications</span>
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}

export default PrimaryNav;