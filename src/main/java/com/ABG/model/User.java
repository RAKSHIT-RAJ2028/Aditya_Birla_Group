package com.ABG.model;

import java.util.List;

import org.hibernate.annotations.WhereJoinTable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name="users")
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;
	
	@Column(unique=true, nullable = false)
    private String username;
	
	@Column(unique=true)
    private String password;
	
	@Column
    private String role;
	
    @Column
    private String name; // Add this field for user's display name
    
    @Column
    private String provider; // Add this field to track auth provider (google, github, etc.)

	@Column(unique = true)
    private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false) // FK in users table
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false) // FK in users table
	private Branch branch;
	

    // Only resource tags where resource_type = 'User'
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ResourceTag> resourceTags;

   
   
	public Long getId() {
		return Id;
		}
		public void setId(Long id) {
			Id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProvider() {
			return provider;
		}
		public void setProvider(String provider) {
			this.provider = provider;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
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



}
