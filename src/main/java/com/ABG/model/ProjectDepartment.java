package com.ABG.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project_departments", 
       uniqueConstraints = {
           @UniqueConstraint(
               name = "uk_project_departments_hierarchy",
               columnNames = {"project_id", "department_name", "parent_department_id"}
           )
       })
@Getter
@Setter
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "department_name", nullable = false)
    @NotBlank(message = "Department name is required")
    private String departmentName;

    @Column(name = "department_description", columnDefinition = "TEXT")
    private String departmentDescription;

    @Column(name = "other_department_details", columnDefinition = "TEXT")
    private String otherDepartmentDetails;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_department_id")
    private ProjectDepartment parentDepartment;

    @OneToMany(mappedBy = "parentDepartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectDepartment> subDepartments = new ArrayList<>();

    @OneToMany(mappedBy = "projectDepartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeProject> employeeProjects = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDescription() {
		return departmentDescription;
	}

	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}

	public String getOtherDepartmentDetails() {
		return otherDepartmentDetails;
	}

	public void setOtherDepartmentDetails(String otherDepartmentDetails) {
		this.otherDepartmentDetails = otherDepartmentDetails;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ProjectDepartment getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(ProjectDepartment parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public List<ProjectDepartment> getSubDepartments() {
		return subDepartments;
	}

	public void setSubDepartments(List<ProjectDepartment> subDepartments) {
		this.subDepartments = subDepartments;
	}

	public Set<EmployeeProject> getEmployeeProjects() {
		return employeeProjects;
	}

	public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
		this.employeeProjects = employeeProjects;
	}
    
    

//    // Business methods
//    public String getFullName() {
//        if (parentDepartment != null) {
//            return parentDepartment.getFullName() + " - " + departmentName;
//        }
//        return project.getName() + " - " + departmentName;
//    }
//
//    public boolean isRootDepartment() {
//        return parentDepartment == null;
//    }
//
//    public boolean isSubDepartment() {
//        return parentDepartment != null;
//    }
//
//    // Convenience method for adding sub-departments
//    public void addSubDepartment(ProjectDepartment subDepartment) {
//        subDepartments.add(subDepartment);
//        subDepartment.setParentDepartment(this);
//    }
//
//    // Convenience method for removing sub-departments
//    public void removeSubDepartment(ProjectDepartment subDepartment) {
//        subDepartments.remove(subDepartment);
//        subDepartment.setParentDepartment(null);
//    }
}