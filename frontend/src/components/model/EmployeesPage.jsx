import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Table, Button, Space, Modal, message, Upload } from 'antd';
import { EditOutlined, DeleteOutlined, UploadOutlined } from '@ant-design/icons';
import axios from 'axios';
import PrimaryNav from '../layout/PrimaryNav';
import './EmployeePage.css';

const EmployeesPage = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [activeFilter, setActiveFilter] = useState("Active");
  const location = useLocation();
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
  });

  // Get breadcrumb from current path
  const getBreadcrumb = () => {
    const path = location.pathname;
    if (path.includes("/employees/list")) return "List of employees";
    if (path.includes("/employees/offered-candidates")) return "Offered Candidates";
    if (path.includes("/employees/import")) return "Import Employees";
    if (path.includes("/employees/onboardings")) return "Onboardings";
    if (path.includes("/employees/offboardings")) return "Offboardings";
    return "Employees";
  };

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
    // Add search functionality here
    fetchEmployees({ search: e.target.value });
  };

  const handleExport = () => {
    console.log("Export functionality");
    // Implement export functionality here
  };

  const handleAddEmployee = () => {
    console.log("Add employee functionality");
    // Implement add employee functionality here
  };

  const fetchEmployees = async (params = {}) => {
    setLoading(true);
    try {
      const page = params.pagination?.current || pagination.current;
      const perPage = params.pagination?.pageSize || pagination.pageSize;

      const response = await axios.get('/api/employees', {
        params: {
          page,
          per_page: perPage,
          status: activeFilter === "All" ? undefined : activeFilter.toLowerCase(),
          search: params.search,
          sortField: params.sortField,
          sortOrder: params.sortOrder,
          ...params.filters,
        },
      });

      setEmployees(response.data.employees);
      setPagination({
        ...pagination,
        total: response.data.meta.total_entries,
      });
    } catch (error) {
      message.error('Failed to fetch employees');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEmployees();
  }, [activeFilter]);

  const handleTableChange = (newPagination, filters, sorter) => {
    fetchEmployees({
      sortField: sorter.field,
      sortOrder: sorter.order,
      pagination: newPagination,
      ...filters,
    });
  };

  const handleEdit = (employee) => {
    // Implement edit functionality
    console.log('Edit employee:', employee);
  };

  const handleDelete = async (employeeId) => {
    try {
      await axios.delete(`/api/employees/${employeeId}`);
      message.success('Employee deleted successfully');
      fetchEmployees();
    } catch (error) {
      message.error('Failed to delete employee');
    }
  };

  const handleImport = async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('currentUserId', 1);  // Replace with actual logged-in user ID or Make it dynamic
    formData.append('projectId', 1);      // Replace with actual project ID or Make it dynamic
    
    try {
      const response = await axios.post('/api/employees/import', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      message.success(response.data.message);
      fetchEmployees();
    } catch (error) {
      message.error(error.response?.data?.error || 'Import failed');
    }
    return false; // Prevent default upload behavior
  };

  const columns = [
    {
      title: 'Employee Code',
      dataIndex: 'employeeCode',
      key: 'employeeCode',
      sorter: true,
    },
    {
      title: 'Full Name',
      dataIndex: 'fullName',
      key: 'fullName',
      sorter: true,
    },
    {
      title: 'Designation',
      dataIndex: 'designation',
      key: 'designation',
      sorter: true,
    },
    {
      title: 'Location',
      dataIndex: 'location',
      key: 'location',
      sorter: true,
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status) => {
        const statusText = status || 'unknown';
        const statusClass = statusText.toLowerCase();
        return (
          <span className={`status-badge ${statusClass}`}>
            {statusText}
          </span>
        );
      },
      filters: [
        { text: 'Active', value: 'active' },
        { text: 'Inactive', value: 'inactive' },
      ],
      onFilter: (value, record) => {
        const recordStatus = record.status ? record.status.toLowerCase() : 'unknown';
        return recordStatus === value;
      },
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <Space size="middle">
          <Button 
            type="primary" 
            icon={<EditOutlined />} 
            onClick={() => handleEdit(record)}
          >
            
          </Button>
          <Button 
            danger 
            icon={<DeleteOutlined />} 
            onClick={() => handleDelete(record.id)}
          >
            
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div className="employee-layout">
      <PrimaryNav />

      <div className="employee-content">
        {/* Header */}
        <header className="employee-header">
          <div className="header-left">
            <button className="menu-toggle">☰</button>
            <div className="breadcrumb">
              <span className="breadcrumb-item">Invoice</span>
              <span className="breadcrumb-separator">›</span>
              <span className="breadcrumb-item">Employees</span>
              <span className="breadcrumb-separator">›</span>
              <span className="breadcrumb-current">{getBreadcrumb()}</span>
            </div>
          </div>

          <div className="header-right">
            <div className="header-nav">
              <span className="nav-item">Social</span>
              <span className="nav-badge">Invoice</span>
              <span className="nav-item">Textile</span>
            </div>
            <div className="header-icons">
              <button className="icon-btn">
                  <i class="fas fa-grip-horizontal"></i>
              </button>
              <button className="icon-btn">
                <i className="fa-solid fa-bell"></i>
              </button>
              <button className="icon-btn">
                <i className="fa-solid fa-moon"></i>
              </button>
              <button className="icon-btn">
                <i className="fa-solid fa-gear"></i>
              </button>
            </div>
          </div>
        </header>

        {/* Main Content */}
        <main className="employee-main">
          <div className="page-header">
            <h1 className="page-title">{getBreadcrumb()}</h1>

            <div className="page-actions">
              <div className="search-container">
                <input
                  type="text"
                  placeholder="Search by Name or Employee Code"
                  value={searchTerm}
                  onChange={handleSearch}
                  className="search-input"
                />
               <span className="search-icon">
                <i className="fa-solid fa-magnifying-glass"></i>
              </span>
              </div>

              <select 
                value={activeFilter} 
                onChange={(e) => setActiveFilter(e.target.value)} 
                className="filter-select"
              >
                <option value="Active">Active</option>
                <option value="Inactive">Inactive</option>
                <option value="All">All</option>
              </select>

              <button className="btn btn-outline" onClick={handleExport}>
                 Export
              </button>

              <Upload
                accept=".xlsx,.xls,.csv"
                beforeUpload={handleImport}
                showUploadList={false}
              >
                <Button type="primary" icon={<UploadOutlined />}>
                  Import Employees
                </Button>
              </Upload>

              <button className="btn btn-primary" onClick={handleAddEmployee}>
                + Add Employee
              </button>
            </div>
          </div>

          {/* Content Area */}
          <div className="content-area">
            {loading ? (
              <div className="loading-container">
                <div className="loading-spinner"></div>
                <h2 className="loading-text">Loading...</h2>
                <p className="loading-description">Employee data will appear here</p>
              </div>
            ) : (
              <Table
                columns={columns}
                rowKey="id"
                dataSource={employees}
                pagination={pagination}
                loading={loading}
                onChange={handleTableChange}
                className="employee-table"
              />
            )}
          </div>
        </main>
      </div>
    </div>
  );
};

export default EmployeesPage;