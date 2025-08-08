package com.ABG.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ABG.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Custom query methods can be added here
    // Example: List<Project> findByCompanyId(Long companyId);
}