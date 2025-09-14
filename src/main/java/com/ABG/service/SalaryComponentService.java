package com.ABG.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ABG.Repository.SalaryComponentRepository;
import com.ABG.model.Company;
import com.ABG.model.Employee;
import com.ABG.model.Project;
import com.ABG.model.SalaryComponent;

@Service
public class SalaryComponentService {
	
	// Autowired
	@Autowired
	private SalaryComponentRepository scRepository;
	
	// constructor of service class
	 public SalaryComponentService(SalaryComponentRepository scRepository) {
			
			this.scRepository = scRepository;
		}
	
	
	// ----------------Methods in service class----------------------------------//
	
	 
	// ---------------------GET-------------------
	public List<SalaryComponent> getAllByCompanyId(Long companyId)
	{
		return scRepository.findByCompany_Id(companyId);
	}
	
	public Optional<SalaryComponent> getById(Long Id)
	{
		return scRepository.findById(Id);
	}
	
	// ---------------------POST-------------------- 
	 public SalaryComponent create(SalaryComponent salaryComponent)
	 {
		 return scRepository.save(salaryComponent);
	 }
	 
	// --------------------PUT--------------------
	    public SalaryComponent update(Long id, SalaryComponent updated) {
	        return scRepository.findById(id).map(existing -> {
	            existing.setName(updated.getName());
	            existing.setIsDeduction(updated.getIsDeduction());
	            existing.setCompany(updated.getCompany()); 
	            return scRepository.save(existing);
	        }).orElseThrow(() -> new RuntimeException("Salary Component not found"));
	    }
	 
	// --------------------DELETE------------------
	 public void delete(Long id)
	 {
		 scRepository.deleteById(id);
	 }
	 
	 
    public void createDefaultSalaryComponents(Company company) {
        // Placeholder logic (do nothing for now)
    }


	public void processEmployeeSalaryComponents(Employee employee, Map<String, String> employeeData, Company company, Project project) {
        // Placeholder logic (do nothing for now)
    }

    
    
}
