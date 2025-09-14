package com.ABG.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ABG.model.SalaryPayout;

public interface SalaryPayoutRepository extends JpaRepository<SalaryPayout , Long>
{

	 // Find payouts by company
     List<SalaryPayout> findByCompany_Id(Long companyId);

    // Find payouts by company and month/year
     @Query("SELECT sp FROM SalaryPayout sp WHERE sp.company.id = :companyId "
     		+ "AND FUNCTION('MONTH', sp.month) = :month "
     		+ "AND FUNCTION('YEAR', sp.month) = :year")
     List<SalaryPayout> findByCompany_IdAndMonthYear(Long companyId , int month , int year );
   
}