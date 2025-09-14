package com.ABG.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ABG.model.SalarySlip;
import com.ABG.service.SalarySlipService;

@RestController
@RequestMapping("api/salary-slip")
public class SalarySlipController 
{

	@Autowired
	private SalarySlipService ssService;
	
	// ---------------------------GET Mapping--------------------------
	
	@GetMapping
	public ResponseEntity<List<SalarySlip>> getByEmployeeId(@RequestParam Long employeeId) {
	    List<SalarySlip> slips = ssService.getAllByEmployee_Id(employeeId);
	    return ResponseEntity.ok(slips);
	}

	
	@GetMapping("{/id}")
	public ResponseEntity<SalarySlip> getBylSalarySlipId(@PathVariable Long id)
	{
		return ssService.getById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public ResponseEntity<List<SalarySlip>> getAllSalarySlipByCompanyIdAndMonthYear(
			@RequestParam Long companyId , 
			@RequestParam Integer month,
			@RequestParam Integer year
			)
	{
		if(month != null && year != null)
		{
			return ResponseEntity.ok(ssService.getAllByCompany_IdAndMonthYear(companyId ,month , year ));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<SalarySlip>> getAllSalarySlipByEmployeeIdAndMonthYear(
			@RequestParam Long employeeId , 
			@RequestParam Integer month,
			@RequestParam Integer year
			)
	{
		if(month != null && year != null)
		{
			return ResponseEntity.ok(ssService.getAllByEmployee_IdAndMonthYear(employeeId , month , year ));
		}
		return ResponseEntity.badRequest().build();
	}
	// ---------------------------PUT Mapping--------------------------
	
	@PutMapping("/{id}")
	public ResponseEntity<SalarySlip> update(@PathVariable Long id , @RequestBody SalarySlip salarySlip)
	{
		return ResponseEntity.ok(ssService.update(id, salarySlip));
	}
	
	// ---------------------------POST Mapping--------------------------
	
	@PostMapping
	public ResponseEntity<SalarySlip> create(@RequestBody SalarySlip salarySlip)
	{
		return ResponseEntity.ok(ssService.create(salarySlip));
	}
	
	
	// ---------------------------DELETE Mapping--------------------------
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		ssService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
