package com.ABG.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Table(name="salary_payouts")
public class SalaryPayout 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Relationship employee_id
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	// Relationship company_id
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	// Relationship salary_slip_id
	@OneToMany(mappedBy = "salaryPayout", cascade = CascadeType.ALL)
	// @JoinColumn(name="salary_slip_id")
	private List<SalarySlip> salarySlip;
	
	private String accountNumber;
	private String description;
	
	private BigDecimal amount;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	private LocalDateTime month;

	// Default constructor
	// Parameterized Constructor
	
	
	// Getter Setter
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

	public List<SalarySlip> getSalarySlip() {
		return salarySlip;
	}

	public void setSalarySlip(List<SalarySlip> salarySlip) {
		this.salarySlip = salarySlip;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public LocalDateTime getMonth() {
		return month;
	}

	public void setMonth(LocalDateTime month) {
		this.month = month;
	}
	
	


	
	

}
