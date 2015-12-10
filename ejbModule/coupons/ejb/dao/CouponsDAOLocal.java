package coupons.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import coupons.ejb.db.Income;

@Local
public interface CouponsDAOLocal {
	
	// called by MDB to store income by JPA
	public void storeIncome(Income income);
	
	// called by IncomeService to provide JPA access for JAX-RS
	public List<Income> viewAllIncomes();
	public List<Income> viewIncomeByCustomer(long customerId);
	public List<Income> viewIncomeByCompany(long companyId);
}
