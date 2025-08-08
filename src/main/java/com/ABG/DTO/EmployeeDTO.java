package com.ABG.DTO;

import com.ABG.model.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDTO {
    private Long id;
    private String fullName;
    private String dpUrlSmall;
    private String employeeCode;
    private String designation;
    private String location;
    private String mobileNumber;
    private String companyEmail;
    private String personalEmail;
    private LocalDate doj;
    private LocalDate dob;
    private String gender;
    private String state;
    private String city;
    private String address;
    private String aadharNumber;
    private String pan;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String uanNumber;
    private String esicNumber;
    private BigDecimal billingFactor;
    private String billingLocation;
    private LocalDate resignedAt;
    private String branchName;
    private String companyName;

    // Default constructor
    public EmployeeDTO() {}

    // Static factory method
    public static EmployeeDTO from(Employee employee) {
        if (employee == null) {
            return null;
        }
        
        EmployeeDTO dto = new EmployeeDTO();
        dto.id = employee.getId();
        dto.fullName = employee.getFullName();
        dto.dpUrlSmall = employee.getDpUrlSmall();
        dto.employeeCode = employee.getEmployeeCode();
        dto.designation = employee.getDesignation();
        dto.location = employee.getLocation();
        dto.mobileNumber = employee.getMobileNumber();
        dto.companyEmail = employee.getCompanyEmail();
        dto.personalEmail = employee.getPersonalEmail();
        dto.doj = employee.getDoj();
        dto.dob = employee.getDob();
        dto.gender = employee.getGender();
        dto.state = employee.getState();
        dto.city = employee.getCity();
        dto.address = employee.getAddress();
        dto.aadharNumber = employee.getAadharNumber();
        dto.pan = employee.getPan();
        dto.bankName = employee.getBankName();
        dto.accountNumber = employee.getAccountNumber();
        dto.ifscCode = employee.getIfscCode();
        dto.uanNumber = employee.getUanNumber();
        dto.esicNumber = employee.getEsicNumber();
        dto.billingFactor = employee.getBillingFactor();
        dto.billingLocation = employee.getBillingLocation();
        dto.resignedAt = employee.getResignedAt();
        
        // Safely extract branch and company names
        if (employee.getBranch() != null) {
            dto.branchName = employee.getBranch().getBranchName();
            if (employee.getBranch().getCompany() != null) {
                dto.companyName = employee.getBranch().getCompany().getName();
            }
        }
        
        return dto;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDpUrlSmall() {
		return dpUrlSmall;
	}

	public void setDpUrlSmall(String dpUrlSmall) {
		this.dpUrlSmall = dpUrlSmall;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public LocalDate getDoj() {
		return doj;
	}

	public void setDoj(LocalDate doj) {
		this.doj = doj;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public String getEsicNumber() {
		return esicNumber;
	}

	public void setEsicNumber(String esicNumber) {
		this.esicNumber = esicNumber;
	}

	public BigDecimal getBillingFactor() {
		return billingFactor;
	}

	public void setBillingFactor(BigDecimal billingFactor) {
		this.billingFactor = billingFactor;
	}

	public String getBillingLocation() {
		return billingLocation;
	}

	public void setBillingLocation(String billingLocation) {
		this.billingLocation = billingLocation;
	}

	public LocalDate getResignedAt() {
		return resignedAt;
	}

	public void setResignedAt(LocalDate resignedAt) {
		this.resignedAt = resignedAt;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

    // Getters and Setters
    
    
    
    
    
}