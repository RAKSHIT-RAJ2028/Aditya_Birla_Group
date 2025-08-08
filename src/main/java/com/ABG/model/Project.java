package com.ABG.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "projects")
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(name = "bill_to_name")
    private String billToName;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "due_days")
    private Integer dueDays;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "is_billed")
    private Boolean isBilled = true;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "yearly_billing_amount_per_month")
    private Integer yearlyBillingAmountPerMonth;

    @Column(name = "has_deductions")
    private Boolean hasDeductions = false;

    @Column(name = "tds_percentage", precision = 5, scale = 2)
    private BigDecimal tdsPercentage;

    @Column(name = "gst_percentage", precision = 5, scale = 2)
    private BigDecimal gstPercentage;

    @Column(name = "security_deposit_percentage", precision = 5, scale = 2)
    private BigDecimal securityDepositPercentage;

    @Column(name = "other_deduction_percentage", precision = 5, scale = 2)
    private BigDecimal otherDeductionPercentage;

    @Column(name = "emd_fdr_date")
    @Temporal(TemporalType.DATE)
    private Date emdFdrDate;

    @Column(name = "emd_fdr_amount", precision = 12, scale = 2)
    private BigDecimal emdFdrAmount;

    @Column(name = "bank_guarantee_date")
    @Temporal(TemporalType.DATE)
    private Date bankGuaranteeDate;

    @Column(name = "bank_guarantee_validity")
    @Temporal(TemporalType.DATE)
    private Date bankGuaranteeValidity;

    @Column(name = "bank_guarantee_amount", precision = 12, scale = 2)
    private BigDecimal bankGuaranteeAmount;

    @Column(name = "car_policy_validity")
    @Temporal(TemporalType.DATE)
    private Date carPolicyValidity;

    @Column(name = "labour_license_validity")
    @Temporal(TemporalType.DATE)
    private Date labourLicenseValidity;

    @Column(name = "workmen_compensation_validity")
    @Temporal(TemporalType.DATE)
    private Date workmenCompensationValidity;

    @Column(name = "personal_accidental_validity")
    @Temporal(TemporalType.DATE)
    private Date personalAccidentalValidity;

    @Column(name = "other_insurance_validity")
    @Temporal(TemporalType.DATE)
    private Date otherInsuranceValidity;

    @Column(name = "vehicle_insurance_validity")
    @Temporal(TemporalType.DATE)
    private Date vehicleInsuranceValidity;

    @Column(name = "number_of_employees")
    private Integer numberOfEmployees;

    @Column(name = "has_general_allowances")
    private Boolean hasGeneralAllowances = false;

    @Column(name = "has_special_allowances")
    private Boolean hasSpecialAllowances = false;

    @Column(name = "has_dearness_allowance")
    private Boolean hasDearnessAllowance = false;

    @Column(name = "has_conveyance_allowance")
    private Boolean hasConveyanceAllowance = false;

    @Column(name = "has_arrears_allowance")
    private Boolean hasArrearsAllowance = false;


    // Relationships
//    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectDepartment> projectDepartments = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeProject> employeeProjects = new HashSet<>();

    
    // Getter Setter
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

	public String getBillToName() {
		return billToName;
	}

	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getDueDays() {
		return dueDays;
	}

	public void setDueDays(Integer dueDays) {
		this.dueDays = dueDays;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public Boolean getIsBilled() {
		return isBilled;
	}

	public void setIsBilled(Boolean isBilled) {
		this.isBilled = isBilled;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getYearlyBillingAmountPerMonth() {
		return yearlyBillingAmountPerMonth;
	}

	public void setYearlyBillingAmountPerMonth(Integer yearlyBillingAmountPerMonth) {
		this.yearlyBillingAmountPerMonth = yearlyBillingAmountPerMonth;
	}

	public Boolean getHasDeductions() {
		return hasDeductions;
	}

	public void setHasDeductions(Boolean hasDeductions) {
		this.hasDeductions = hasDeductions;
	}

	public BigDecimal getTdsPercentage() {
		return tdsPercentage;
	}

	public void setTdsPercentage(BigDecimal tdsPercentage) {
		this.tdsPercentage = tdsPercentage;
	}

	public BigDecimal getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(BigDecimal gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	public BigDecimal getSecurityDepositPercentage() {
		return securityDepositPercentage;
	}

	public void setSecurityDepositPercentage(BigDecimal securityDepositPercentage) {
		this.securityDepositPercentage = securityDepositPercentage;
	}

	public BigDecimal getOtherDeductionPercentage() {
		return otherDeductionPercentage;
	}

	public void setOtherDeductionPercentage(BigDecimal otherDeductionPercentage) {
		this.otherDeductionPercentage = otherDeductionPercentage;
	}

	public Date getEmdFdrDate() {
		return emdFdrDate;
	}

	public void setEmdFdrDate(Date emdFdrDate) {
		this.emdFdrDate = emdFdrDate;
	}

	public BigDecimal getEmdFdrAmount() {
		return emdFdrAmount;
	}

	public void setEmdFdrAmount(BigDecimal emdFdrAmount) {
		this.emdFdrAmount = emdFdrAmount;
	}

	public Date getBankGuaranteeDate() {
		return bankGuaranteeDate;
	}

	public void setBankGuaranteeDate(Date bankGuaranteeDate) {
		this.bankGuaranteeDate = bankGuaranteeDate;
	}

	public Date getBankGuaranteeValidity() {
		return bankGuaranteeValidity;
	}

	public void setBankGuaranteeValidity(Date bankGuaranteeValidity) {
		this.bankGuaranteeValidity = bankGuaranteeValidity;
	}

	public BigDecimal getBankGuaranteeAmount() {
		return bankGuaranteeAmount;
	}

	public void setBankGuaranteeAmount(BigDecimal bankGuaranteeAmount) {
		this.bankGuaranteeAmount = bankGuaranteeAmount;
	}

	public Date getCarPolicyValidity() {
		return carPolicyValidity;
	}

	public void setCarPolicyValidity(Date carPolicyValidity) {
		this.carPolicyValidity = carPolicyValidity;
	}

	public Date getLabourLicenseValidity() {
		return labourLicenseValidity;
	}

	public void setLabourLicenseValidity(Date labourLicenseValidity) {
		this.labourLicenseValidity = labourLicenseValidity;
	}

	public Date getWorkmenCompensationValidity() {
		return workmenCompensationValidity;
	}

	public void setWorkmenCompensationValidity(Date workmenCompensationValidity) {
		this.workmenCompensationValidity = workmenCompensationValidity;
	}

	public Date getPersonalAccidentalValidity() {
		return personalAccidentalValidity;
	}

	public void setPersonalAccidentalValidity(Date personalAccidentalValidity) {
		this.personalAccidentalValidity = personalAccidentalValidity;
	}

	public Date getOtherInsuranceValidity() {
		return otherInsuranceValidity;
	}

	public void setOtherInsuranceValidity(Date otherInsuranceValidity) {
		this.otherInsuranceValidity = otherInsuranceValidity;
	}

	public Date getVehicleInsuranceValidity() {
		return vehicleInsuranceValidity;
	}

	public void setVehicleInsuranceValidity(Date vehicleInsuranceValidity) {
		this.vehicleInsuranceValidity = vehicleInsuranceValidity;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public Boolean getHasGeneralAllowances() {
		return hasGeneralAllowances;
	}

	public void setHasGeneralAllowances(Boolean hasGeneralAllowances) {
		this.hasGeneralAllowances = hasGeneralAllowances;
	}

	public Boolean getHasSpecialAllowances() {
		return hasSpecialAllowances;
	}

	public void setHasSpecialAllowances(Boolean hasSpecialAllowances) {
		this.hasSpecialAllowances = hasSpecialAllowances;
	}

	public Boolean getHasDearnessAllowance() {
		return hasDearnessAllowance;
	}

	public void setHasDearnessAllowance(Boolean hasDearnessAllowance) {
		this.hasDearnessAllowance = hasDearnessAllowance;
	}

	public Boolean getHasConveyanceAllowance() {
		return hasConveyanceAllowance;
	}

	public void setHasConveyanceAllowance(Boolean hasConveyanceAllowance) {
		this.hasConveyanceAllowance = hasConveyanceAllowance;
	}

	public Boolean getHasArrearsAllowance() {
		return hasArrearsAllowance;
	}

	public void setHasArrearsAllowance(Boolean hasArrearsAllowance) {
		this.hasArrearsAllowance = hasArrearsAllowance;
	}

	public Set<ProjectDepartment> getProjectDepartments() {
		return projectDepartments;
	}

	public void setProjectDepartments(Set<ProjectDepartment> projectDepartments) {
		this.projectDepartments = projectDepartments;
	}

	public Set<EmployeeProject> getEmployeeProjects() {
		return employeeProjects;
	}

	public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
		this.employeeProjects = employeeProjects;
	}

//    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ProjectUser> projectUsers = new HashSet<>();

//    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Vehicle> vehicles = new HashSet<>();
    
    
    


}
