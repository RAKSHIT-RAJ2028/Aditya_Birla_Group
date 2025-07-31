package com.ABG.model;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_projects")
public class EmployeeProject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "project_department_id")
    private ProjectDepartment projectDepartment;
    
    @Column(name= "assignment_date")
    private LocalDate assignmentDate;
    
    
    
    public EmployeeProject() {
		
	}

	public EmployeeProject(Long id, Employee employee, Project project, ProjectDepartment projectDepartment,
			LocalDate assignmentDate) {
		super();
		this.id = id;
		this.employee = employee;
		this.project = project;
		this.projectDepartment = projectDepartment;
		this.assignmentDate = assignmentDate;
	}

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectDepartment getProjectDepartment() {
		return projectDepartment;
	}

	public void setProjectDepartment(ProjectDepartment projectDepartment) {
		this.projectDepartment = projectDepartment;
	}

	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
    
   
    
    
}