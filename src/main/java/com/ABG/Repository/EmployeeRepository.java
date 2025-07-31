package com.ABG.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ABG.model.Employee;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
//	Optional<Employee> findByEmployeeCodeAndCompanyId(String employeeCode, Long companyId);
    
    @Query("SELECT e FROM Employee e WHERE " +
        "LOWER(COALESCE(e.employeeCode, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
        "LOWER(COALESCE(e.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
        "LOWER(COALESCE(e.designation, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
        "LOWER(COALESCE(e.location, '')) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Employee> searchEmployees(@Param("search") String search, Pageable pageable);


	Optional<Employee> findByEmployeeCodeAndCompanyId(String employeeCode, Long id);
}