package com.ABG.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ABG.model.ProjectDepartment;

public interface ProjectDepartmentRepository extends JpaRepository<ProjectDepartment, Long> {

    // Parent department (no parentDepartment specified)
    Optional<ProjectDepartment> findByDepartmentNameAndProjectId(String departmentName, Long projectId);

    // Sub-department (has parentDepartment)
    Optional<ProjectDepartment> findByDepartmentNameAndProjectIdAndParentDepartmentId(
        String departmentName, Long projectId, Long parentDepartmentId);
}