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
@Table(name="salary_payouts_salary_components")
public class SalaryPayoutSalaryComponent
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="salary_payout_id")
	private SalaryPayout salaryPayout;
	
	@ManyToOne
	@JoinColumn(name="salary_component_id")
	private SalaryComponent  salaryComponent;
	
	private  BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SalaryPayout getSalaryPayout() {
		return salaryPayout;
	}

	public void setSalaryPayout(SalaryPayout salaryPayout) {
		this.salaryPayout = salaryPayout;
	}

	public SalaryComponent getSalaryComponent() {
		return salaryComponent;
	}

	public void setSalaryComponent(SalaryComponent salaryComponent) {
		this.salaryComponent = salaryComponent;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	
	
}
