import React from 'react';
import { useLocation } from 'react-router-dom';
import './SecondaryNav.css';

function SecondaryNav() {
  const location = useLocation();
  
  // Material sub-nav
  const materialNav = [
    { path: '/materials/list', label: 'All Materials' },
    { path: '/materials/properties', label: 'Properties' },
    { path: '/materials/add', label: 'Add New' }
  ];
  
  // Inventory sub-nav
  const inventoryNav = [
    { path: '/inventory/stock', label: 'Stock Levels' },
    { path: '/inventory/pricing', label: 'Pricing' },
    { path: '/inventory/movements', label: 'Movements' }
  ];
  
  // Admin only nav
  const adminNav = [
    { path: '/employees', label: 'Employees' },
    { path: '/payments', label: 'Payments' },
    { path: '/reports', label: 'Reports' }
  ];

  let currentNav = [];
  
  if (location.pathname.includes('/materials')) {
    currentNav = materialNav;
  } else if (location.pathname.includes('/inventory')) {
    currentNav = inventoryNav;
  } else if (location.pathname.startsWith('/admin')) {
    currentNav = [...materialNav, ...inventoryNav, ...adminNav];
  }

  return (
    <nav className="secondary-nav">
      <ul>
        {currentNav.map((item) => (
          <li key={item.path}>
            <a href={item.path} className={location.pathname === item.path ? 'active' : ''}>
              {item.label}
            </a>
          </li>
        ))}
      </ul>
    </nav>
  );
}

export default SecondaryNav;