import React, { useState, useEffect } from 'react';
import { Table, Button, Space, Modal, message, Upload } from 'antd';
import { EditOutlined, DeleteOutlined, UploadOutlined } from '@ant-design/icons';
import axios from 'axios';

const EmployeesPage = () => {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
  });

  const fetchEmployees = async (params = {}) => {
    setLoading(true);
    try {
      const page = params.pagination?.current || pagination.current;
      const perPage = params.pagination?.pageSize || pagination.pageSize;

      const response = await axios.get('/api/employees', {
        params: {
          page,
          per_page: perPage,
          sortField: params.sortField,
          sortOrder: params.sortOrder,
          ...params.filters, // if you want to support filtering
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
  }, []);

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
    formData.append('currentUserId', 1);  // Replace with actual logged-in user ID or Make it dynamic (NOT HARDCODED)
    formData.append('projectId', 1);      // Replace with actual project ID or Make it dynamic (NOT HARDCODED)
    
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
    },
    {
      title: 'Full Name',
      dataIndex: 'fullName',
      key: 'fullName',
    },
    {
      title: 'Designation',
      dataIndex: 'designation',
      key: 'designation',
    },
    {
      title: 'Location',
      dataIndex: 'location',
      key: 'location',
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
            Edit
          </Button>
          <Button 
            danger 
            icon={<DeleteOutlined />} 
            onClick={() => handleDelete(record.id)}
          >
            Delete
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div className="employees-page">
      <div style={{ marginBottom: 16 }}>
        <Upload
          accept=".xlsx,.xls,.csv"
          beforeUpload={handleImport}
          showUploadList={false}
        >
          <Button type="primary" icon={<UploadOutlined />}>
            Import Employees
          </Button>
        </Upload>
      </div>
      
      <Table
        columns={columns}
        rowKey="id"
        dataSource={employees}
        pagination={pagination}
        loading={loading}
        onChange={handleTableChange}
      />
    </div>
  );
};

export default EmployeesPage;