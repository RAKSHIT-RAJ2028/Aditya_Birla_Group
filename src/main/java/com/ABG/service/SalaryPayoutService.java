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
	
	
	
	
	
	
	// ---------------------------POST Method--------------------------
	
	
	
	
	
	// ---------------------------DELETE Method--------------------------

	
}
