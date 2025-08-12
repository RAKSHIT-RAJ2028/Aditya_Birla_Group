package com.ABG.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="salary_components")
public class SalaryComponent 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Boolean isDeduction =false;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	//Relationship
	
	@ManyToOne
	@JoinColumn(name ="company_id")
	private Company company;
	
	@OneToMany(mappedBy = "salaryComponent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CtcSalaryComponent> ctcSalaryComponents = new ArrayList<>();

	
	
	
	// Default constructor
	public SalaryComponent() {
		
	}

	// Parameterized constructor
	public SalaryComponent(Long id, String name, Boolean isDeduction, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.isDeduction = isDeduction;
		this.company = company;
	}

	// Getters Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsDeduction() {
		return isDeduction;
	}

	public void setIsDeduction(Boolean isDeduction) {
		this.isDeduction = isDeduction;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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
	
	
	
	

}
