package com.ABG.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="salary_components_salary_slips")
public class SalaryComponentSalarySlip 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// salary_component_id
	@ManyToOne
	@JoinColumn(name="salary_component_id")
	private SalaryComponent salaryComponent;
	
	// salary_slip_id
	@ManyToOne
	@JoinColumn(name="salary_slip_id")
	private SalarySlip salarySlip;
	
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SalaryComponent getSalaryComponent() {
		return salaryComponent;
	}

	public void setSalaryComponent(SalaryComponent salaryComponent) {
		this.salaryComponent = salaryComponent;
	}

	public SalarySlip getSalarySlip() {
		return salarySlip;
	}

	public void setSalarySlip(SalarySlip salarySlip) {
		this.salarySlip = salarySlip;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	
}
