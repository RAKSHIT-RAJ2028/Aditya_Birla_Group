package com.ABG.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ABG.Repository.SalaryPayoutRepository;
import com.ABG.model.SalaryPayout;

@Service
public class SalaryPayoutService 
{
	@Autowired
	private SalaryPayoutRepository spRepository;
	
	
	
	// ---------------------------GET Method--------------------------
	public List<SalaryPayout> getAllByCompanyId(Long companyId)
	{
		return spRepository.findByCompany_Id(companyId);
		
	}
	
	public List<SalaryPayout> getAllByCompanyIdAndMonthYear(Long companyId , int month , int year) {
		return spRepository.findByCompany_IdAndMonthYear(companyId , month , year);
	}
	
	public Optional<SalaryPayout> getById(Long Id)
	{
		return spRepository.findById(Id);
	}
	
	// ---------------------------PUT Method--------------------------
	public SalaryPayout update(Long id, SalaryPayout updated) 
	{
		return spRepository.findById(id).map(existing -> {
			existing.setEmployee(updated.getEmployee());
			existing.setAccountNumber(updated.getAccountNumber());
			existing.setAmount(updated.getAmount());
			existing.setDescription(updated.getDescription());
			existing.setMonth(updated.getMonth());
			existing.setCompany(updated.getCompany());
			return spRepository.save(existing);
			
	    }).orElseThrow(() -> new RuntimeException("Salary Payout not found"));
	}
	
	// ---------------------------POST Method--------------------------
	
	public SalaryPayout create(SalaryPayout salaryPayout)
	{
		return spRepository.save(salaryPayout);
	}
	
	// ---------------------------DELETE Method--------------------------

	public void delete(Long id)
	{
		spRepository.deleteById(id);
	}
	
}
