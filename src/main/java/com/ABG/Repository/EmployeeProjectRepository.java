package com.ABG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ABG.model.EmployeeProject;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
    
    boolean existsByEmployeeIdAndProjectIdAndProjectDepartmentId(
        Long employeeId, Long projectId, Long projectDepartmentId);
    
    // Example custom query:
    // List<EmployeeProject> findByEmployeeId(Long employeeId);
}