package com.ABG.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String dpUrlSmall;


    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    private LocalDate dob;
    private LocalDate doj;
    private String employeeCode;
    private String location;
    private String accountNumber;
    private String pan;
    private String companyEmail;
    private String personalEmail;
    private String billingLocation = "Noida";

    @Column(precision = 10, scale = 2)
    private BigDecimal billingFactor;

    private LocalDate resignedAt;
    private String designation;
    private String ifscCode;
    private String aadharCard;
    private String fathersName;
    private String husbandsName;
    private String gender;
    private String highestQualification;
    private String yearOfPassout;
    private String yearOfExperience;
    private String orgDetails;
    private String mobileNumber;
    private String aadharNumber;
    private String drivingLicenseNumber;
    private String policeVerificationAndValidity;
    private String uanNumber;
    private String esicNumber;
    private String resume;
    private String signature;
    private String state;
    private String city;
    private String bankName;
    private String address;
    
    public LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public LocalDate getDoj() {
		return doj;
	}

	public void setDoj(LocalDate doj) {
		this.doj = doj;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
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

	public String getBillingLocation() {
		return billingLocation;
	}

	public void setBillingLocation(String billingLocation) {
		this.billingLocation = billingLocation;
	}

	public BigDecimal getBillingFactor() {
		return billingFactor;
	}

	public void setBillingFactor(BigDecimal billingFactor) {
		this.billingFactor = billingFactor;
	}

	public LocalDate getResignedAt() {
		return resignedAt;
	}

	public void setResignedAt(LocalDate resignedAt) {
		this.resignedAt = resignedAt;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getHusbandsName() {
		return husbandsName;
	}

	public void setHusbandsName(String husbandsName) {
		this.husbandsName = husbandsName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}

	public String getYearOfPassout() {
		return yearOfPassout;
	}

	public void setYearOfPassout(String yearOfPassout) {
		this.yearOfPassout = yearOfPassout;
	}

	public String getYearOfExperience() {
		return yearOfExperience;
	}

	public void setYearOfExperience(String yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	public String getOrgDetails() {
		return orgDetails;
	}

	public void setOrgDetails(String orgDetails) {
		this.orgDetails = orgDetails;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public String getPoliceVerificationAndValidity() {
		return policeVerificationAndValidity;
	}

	public void setPoliceVerificationAndValidity(String policeVerificationAndValidity) {
		this.policeVerificationAndValidity = policeVerificationAndValidity;
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

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
    
    
}
