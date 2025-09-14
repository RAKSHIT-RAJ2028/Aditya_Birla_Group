package com.ABG.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ABG.model.SalaryComponent;

public interface SalaryComponentRepository extends JpaRepository<SalaryComponent , Long>
{
	List<SalaryComponent> findByCompany_Id(Long companyId);
	

	
}
