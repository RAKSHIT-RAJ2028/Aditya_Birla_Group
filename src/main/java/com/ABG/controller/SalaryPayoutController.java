package com.ABG.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ABG.model.SalaryPayout;
import com.ABG.service.SalaryPayoutService;

@RestController
@RequestMapping("/api/salary-payout")
public class SalaryPayoutController 
{
	
	private SalaryPayoutService spService;
	
	
	
	// ---------------------------GET Mapping--------------------------
	
	@GetMapping
	public ResponseEntity<List<SalaryPayout>> getAllSalaryPayout(@RequestParam Long companyId)
	{
		return ResponseEntity.ok(spService.getAllByCompanyId(companyId));
	}
	
	@GetMapping 
	public ResponseEntity<List<SalaryPayout>> getAllSalaryPayoutByMonthYear(@RequestParam Long companyId , @RequestParam(required = false) Integer month ,@RequestParam(required = false) Integer year)
	{
		if(month != null && year != null)
		{
			return ResponseEntity.ok(spService.getAllByCompanyIdAndMonthYear(companyId, month, year));
		}
		else
			return ResponseEntity.ok(spService.getAllByCompanyId(companyId));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SalaryPayout> getById(@PathVariable Long id)
	{
		return spService.getById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	// ---------------------------PUT Mapping--------------------------
		
		
		
		
		
		
	// ---------------------------POST Mapping--------------------------
		
		
		
		
		
	// ---------------------------DELETE Mapping--------------------------

}
