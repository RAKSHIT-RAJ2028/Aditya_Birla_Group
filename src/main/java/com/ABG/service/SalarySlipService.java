package com.ABG.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ABG.Repository.SalarySlipRepository;
import com.ABG.model.SalarySlip;


@Service
public class SalarySlipService 
{

	@Autowired
	private SalarySlipRepository ssRepository;
	
	
	// ---------------------------GET----Method--------------------------
	 
	// Get all Salary SLip by SalaryPayout id
	public List<SalarySlip> getAllBySalaryPayout_Id(Long salaryPayoutId)
	{
		return ssRepository.findBySalaryPayout_Id(salaryPayoutId);	
	}
	// Get all Salary SLip by Employee id
	public List<SalarySlip> getAllByEmployee_Id(Long employeeId)
	{
		return ssRepository.findByEmployee_Id(employeeId);
	}
	// Get all Salary SLip by Company id
	public List<SalarySlip> getAllByCompanyId(Long comapanyId)
	{
		return ssRepository.findByCompanyId(comapanyId);
	}
	// Get all Salary SLip by Company_Id and Month Year
	public List<SalarySlip> getAllByCompany_IdAndMonthYear(Long companyId , int month ,int year)
	{
		return ssRepository.findByCompany_IdAndMonthYear(companyId, month, year);
	}
	// Get all Salary SLip by Employee_Id and Month Year
	public List<SalarySlip> getAllByEmployee_IdAndMonthYear(Long employeeId , int month , int year)
	{
		return ssRepository.findByEmployee_IdAndMonthYear(employeeId, month, year);
	}
	
	public Optional<SalarySlip> getById(Long Id)
	{
		return ssRepository.findById(Id);
	}
	
	// ---------------------------PUT----Method--------------------------
	
    public SalarySlip update(Long id, SalarySlip updated) {
        return ssRepository.findById(id).map(existing -> {
            existing.setAmount(updated.getAmount());
            existing.setMonth(updated.getMonth());
            existing.setEmployee(updated.getEmployee());
            existing.setSalaryPayout(updated.getSalaryPayout());
            existing.setTotalWorkingDays(updated.getTotalWorkingDays());
            existing.setTotalLeavesTaken(updated.getTotalLeavesTaken());
            existing.setTotalPresent(updated.getTotalPresent());
            existing.setTotalAbsent(updated.getTotalAbsent());
            existing.setWeeklyOff(updated.getWeeklyOff());
            existing.setExtraDays(updated.getExtraDays());
            existing.setPaidLeaves(updated.getPaidLeaves());
            existing.setUnpaidLeaves(updated.getUnpaidLeaves());
            return ssRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Salary Slip not found"));
    }
	
	// ---------------------------POST---Method--------------------------


	public SalarySlip create(SalarySlip salarySlip)
	{
		return ssRepository.save(salarySlip);
	}
	
	
	// ---------------------------DELETE-Method--------------------------
	
	public void delete(Long id)
	{
		ssRepository.deleteById(id);
	}
}
