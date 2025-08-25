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

import com.ABG.model.SalaryComponent;
import com.ABG.service.SalaryComponentService;



@RestController
@RequestMapping("api/salary-component")
public class SalaryComponentController 
{
	@Autowired
	private SalaryComponentService scService;
	
//-------------------------GET MAPPING-------------------
@GetMapping
public ResponseEntity<List<SalaryComponent>> getAllSalaryComponent(@RequestParam Long companyId)
{
	return ResponseEntity.ok(scService.getAllByCompanyId(companyId));
}
	
@GetMapping("/{id}")
public ResponseEntity<SalaryComponent> getById(@PathVariable Long id)
{
	return scService.getById(id)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
			
}

//-------------------------POST MAPPING-------------------
@PostMapping
public ResponseEntity<SalaryComponent> create(@RequestBody SalaryComponent salaryCompoment)
{
	return ResponseEntity.ok(scService.create(salaryCompoment));
	
}

//-------------------------PUT MAPPING-------------------
@PutMapping("/{id}")
public ResponseEntity<SalaryComponent> update(@PathVariable Long id, @RequestBody SalaryComponent updated) {
    return ResponseEntity.ok(scService.update(id, updated));
}


//-------------------------DELETE MAPPING-------------------
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id)
{
	scService.delete(id);
	return ResponseEntity.noContent().build();
}

}
