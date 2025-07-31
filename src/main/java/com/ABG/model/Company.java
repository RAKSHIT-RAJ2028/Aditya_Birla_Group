package com.ABG.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "companies")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String headOffice;
	private String email;
	private String website;
	private String phone;
	private String cin;
	private String regdOffice;
	
	public Company(Long id, String name, String headOffice, String email, String website, String phone, String cin,
			String regdOffice) {
		
		this.id = id;
		this.name = name;
		this.headOffice = headOffice;
		this.email = email;
		this.website = website;
		this.phone = phone;
		this.cin = cin;
		this.regdOffice = regdOffice;
	}

	public Company() {
	
	}

	// Getter - Setters
	
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

	public String getHeadOffice() {
		return headOffice;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getRegdOffice() {
		return regdOffice;
	}

	public void setRegdOffice(String regdOffice) {
		this.regdOffice = regdOffice;
	}
	
    
}
