package com.ABG.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="salary_slips")
public class SalarySlip
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime month;
	
	// Relationship employee_id  
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	// Relationship salary_payouts_id  
	@ManyToOne
	@JoinColumn(name="salary_payouts_id")
	private SalaryPayout salaryPayout;
	
	private BigDecimal amount;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@Column(name="total_working_days")
	private int totalWorkingDays;
	
	@Column(name="taoal_leaves_taken")
	private int totalLeavesTaken;
	
	@Column(name="total_present")
	private int totalPresent;
	
	@Column(name="total_absent")
	private int totalAbsent;
	
	@Column(name="weekly_off")
	private int weeklyOff;
	
	@Column(name="extra_days")
	private int extraDays;
	
	@Column(name="paid_leaves")
	private int paidLeaves;
	
	@Column(name="unpaid_leaves")
	private int unpaidLeaves;

	// Getter Setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getMonth() {
		return month;
	}

	public void setMonth(LocalDateTime month) {
		this.month = month;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public SalaryPayout getSalaryPayout() {
		return salaryPayout;
	}

	public void setSalaryPayout(SalaryPayout salaryPayout) {
		this.salaryPayout = salaryPayout;
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

	public int getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(int totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public int getTotalLeavesTaken() {
		return totalLeavesTaken;
	}

	public void setTotalLeavesTaken(int totalLeavesTaken) {
		this.totalLeavesTaken = totalLeavesTaken;
	}

	public int getTotalPresent() {
		return totalPresent;
	}

	public void setTotalPresent(int totalPresent) {
		this.totalPresent = totalPresent;
	}

	public int getTotalAbsent() {
		return totalAbsent;
	}

	public void setTotalAbsent(int totalAbsent) {
		this.totalAbsent = totalAbsent;
	}

	public int getWeeklyOff() {
		return weeklyOff;
	}

	public void setWeeklyOff(int weeklyOff) {
		this.weeklyOff = weeklyOff;
	}

	public int getExtraDays() {
		return extraDays;
	}

	public void setExtraDays(int extraDays) {
		this.extraDays = extraDays;
	}

	public int getPaidLeaves() {
		return paidLeaves;
	}

	public void setPaidLeaves(int paidLeaves) {
		this.paidLeaves = paidLeaves;
	}

	public int getUnpaidLeaves() {
		return unpaidLeaves;
	}

	public void setUnpaidLeaves(int unpaidLeaves) {
		this.unpaidLeaves = unpaidLeaves;
	}
	
	// Default constructor
	// Parameterized Constructor
		
		
	// Getter Setter
	
	
	
	
	
	
	
	

}
