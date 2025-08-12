package com.ABG.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ctcs_salary_components")
public class CtcSalaryComponent 
{
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal amount;
	private String frequency;
	
	@ManyToOne
	@JoinColumn(name="ctc_id")
	private Ctc ctc;
	
	@ManyToOne
	@JoinColumn(name="salary_component_id")
	private SalaryComponent salaryComponent;
	
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	
	

}
