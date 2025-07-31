package com.ABG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ABG.model.EmployeeUser;

@Repository
public interface EmployeeUserRepository extends JpaRepository<EmployeeUser, Long> {
    
    boolean existsByEmployeeIdAndUserId(Long employeeId, Long userId);
    
    // Example custom query:
    // List<EmployeeUser> findByUserId(Long userId);
}