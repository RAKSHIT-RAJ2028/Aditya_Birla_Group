"use client"

import React, { useState } from "react"
import { NavLink, useLocation } from "react-router-dom"
import "./PrimaryNav.css"

function PrimaryNav() {
  const [isCollapsed, setIsCollapsed] = useState(false)
  const [expandedMenu, setExpandedMenu] = useState(["employees"])
  const location = useLocation()

  const toggleMenu = () => {
    setIsCollapsed(!isCollapsed)
  }

  const toggleSubMenu = (menu) => {
    if (expandedMenu.includes(menu)) {
      setExpandedMenu(expandedMenu.filter((item) => item !== menu))
    } else {
      setExpandedMenu([...expandedMenu, menu])
    }
  }

  const navItems = [
    { path: "/dashboard", iconClass: "fa-solid fa-chart-bar", label: "Dashboard" },
    {
      path: "/employees",
      iconClass: "fa-solid fa-users",
      label: "Employees",
      subItems: [
        { path: "/employees/list", label: "List" },
        { path: "/employees/offered-candidates", label: "Offered Candidates" },
        { path: "/employees/import", label: "Import Employees" },
        { path: "/employees/onboardings", label: "Onboardings" },
        { path: "/employees/offboardings", label: "Offboardings" },
      ],
    },
    { path: "/projects", iconClass: "fa-solid fa-folder", label: "Projects" },
    { path: "/resource-matrix", iconClass: "fa-solid fa-clipboard", label: "Resource Matrix" },
    { path: "/attendance", iconClass: "fa-solid fa-calendar-days", label: "Attendance" },
    { path: "/settings", iconClass: "fa-solid fa-gear", label: "Settings",
       subItems: [
        { path: "/settings/user", label: "Users" },
        { path: "/settings/company", label: "Company" },
        { path: "/settings/branch", label: "Branch" },
        { path: "/settings/Bank Accounts", label: "Bank Accounts" },
        { path: "/settings/salary component", label: "Salary Component" },
      ],
     },
    { path: "/awards", iconClass: "fa-solid fa-trophy", label: "Awards" },
    { path: "/leaves", iconClass: "fa-solid fa-umbrella-beach", label: "Leaves" },
    { path: "/holidays", iconClass: "fa-solid fa-calendar-check", label: "Holidays" },
    { path: "/salary-slip", iconClass: "fas fa-receipt", label: "Salary Slip" },
  ]


  return (
    <div className={`main-layout ${isCollapsed ? "close-sidebar" : ""}`}>
      <div className={`hrms-sidebar ${isCollapsed ? "sidebar-nav-collapsed" : ""}`}>
        <div id="wrapper">
          <div id="sidebar-wrapper">
            <ul className="sidebar-nav">
              {/* Company Header */}
              <li className="empty_li">
                <div className="company-header">
                  <div className="company-logo">
                    <div className={`logo-icon ${isCollapsed ? "collapsed" : ""}`}>ABG</div>
                    {!isCollapsed && (
                      <div className="company-info">
                        <span className="company-name">ADITYA BIRLA GROUP</span>
                        <span className="user-name-header">RAJ SHARMA</span>
                      </div>
                    )}
                  </div>
                </div>
              </li>

              {/* User Profile Section */}
              {!isCollapsed && (
                <li className="sidebar-user-details">
                  <div className="user-profile-container">
                    <div className="user-profile-image">RS</div>
                    <div className="user-data">
                      <div className="user-name">Raj Sharma</div>
                      <div className="user-designation">Java Developer</div>
                    </div>
                  </div>
                </li>
              )}

              {/* Collapsed User Profile */}
              {isCollapsed && (
                <li className="sidebar-user-details collapsed">
                  <div className="user-profile-image collapsed">RS</div>
                </li>
              )}

              {/* Navigation Items */}
              {navItems.map((item) => {
                const isActive = location.pathname === item.path
                const hasActiveSubItem = item.subItems?.some((subItem) => location.pathname === subItem.path)
                const isMenuActive = isActive || hasActiveSubItem
                const isExpanded = expandedMenu.includes(item.path.replace("/", ""))

                return (
                  <React.Fragment key={item.path}>
                    <li className={isMenuActive ? "li-active" : ""}>
                      <NavLink
                        // to={item.subItems ? "#" : item.path}
                         to={item.path} // Always navigate
                        className={({ isActive: linkActive }) => (linkActive || isMenuActive ? "active" : "")}
                        onClick={(e) => {
                          if (item.subItems) {
                            // e.preventDefault()
                            toggleSubMenu(item.path.replace("/", ""))
                          }
                        }}
                      >
                        <span className="sidebar-icon-wrapper">
                        <span className="sidebar-icon">
                          <i className={item.iconClass}></i>
                        </span>

                        </span>
                      {!isCollapsed && (
                        <>
                          <span className="sidebar-title nav-item">{item.label}</span>
                          {item.subItems && (
                            <i
                              className={`chevron-icon fa-solid ${isExpanded ? "fa-chevron-down" : "fa-chevron-right"}`}
                            ></i>
                          )}
                        </>
                      )}
                        <div className="vertical-bar"></div>
                      </NavLink>
                    </li>

                    {/* Sub Menu */}
                    {item.subItems && isExpanded && !isCollapsed && (
                      <li>
                        <ul className="panel-collapse">
                          {item.subItems.map((subItem) => (
                            <li key={subItem.path}>
                              <NavLink to={subItem.path} className={({ isActive }) => (isActive ? "active" : "")}>
                                <span className="sidebar-icon-wrapper">
                                  <span className="sidebar-info-icon">•</span>
                                </span>
                                <span className="nav-item">{subItem.label}</span>
                              </NavLink>
                            </li>
                          ))}
                        </ul>
                      </li>
                    )}
                  </React.Fragment>
                )
              })}

              {/* Footer */}
              {!isCollapsed && (
                <li className="sidebar-footer">
                  <div className="powered-by">
                    <span>Powered By: </span>
                    <span className="brand">Aditya Birla Group</span>
                  </div>
                </li>
              )}
            </ul>
          </div>
        </div>
      </div>

      {/* External Hamburger Button */}
      <div className={`sidebar-hamburger ${isCollapsed ? "shrink-sidebar" : ""}`}>
        <button className="sidebar-collapse-btn" onClick={toggleMenu} aria-label="Toggle sidebar">
          <span className="hamburger-icon">☰</span>
        </button>
      </div>
    </div>
  )
}

export default PrimaryNav
