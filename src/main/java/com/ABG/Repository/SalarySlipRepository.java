package com.ABG.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ABG.model.SalarySlip;

public interface SalarySlipRepository extends JpaRepository< SalarySlip , Long>
{

	// 1. Fetch by SalaryPayout Id
	List<SalarySlip> findBySalaryPayout_Id(Long salaryPayoutId);
	
	// 2. Fetch by EmoloyeeId
	List<SalarySlip> findByEmployee_Id(Long employeeId);
	
	// 3. Fetch by company (via payout)
    @Query("SELECT s FROM SalarySlip s "
    		+ "WHERE s.salaryPayout.company.id = :companyId")
    List<SalarySlip> findByCompanyId(Long companyId);
    
	// 4. Fetch by company and month/year (already present)
	@Query(" SELECT s FROM SalarySlip s "
			+ "WHERE s.salaryPayout.company.id  = :companyId"
			+ "AND FUNCTION('MONTH' , s.month) = :month"
			+ "AND FUNCTION('YEAR'  , s.month) = :year")
	List<SalarySlip> findByCompany_IdAndMonthYear(Long companyId , int Month , int Year);
	
	
	// 5. Fetch by employee & month/year
	@Query(" SELECT s FROM SalarySlip s "
			+ "WHERE s.employee.id  = :employeeId"
			+ "AND FUNCTION('MONTH' , s.month) = :month"
			+ "AND FUNCTION('YEAR'  , s.month) = :year")
	List<SalarySlip> findByEmployee_IdAndMonthYear(Long employeeId , int Month , int Year);
	
}
