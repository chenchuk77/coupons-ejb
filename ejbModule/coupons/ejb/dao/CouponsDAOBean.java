package coupons.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import coupons.ejb.beans.IncomeType;
import coupons.ejb.db.Income;

@Stateless
public class CouponsDAOBean implements CouponsDAOLocal {
	
	// unit name exists in persistence.xml
	@PersistenceContext(unitName="couponSystem")
	private EntityManager manager;

	// called by MDB to store income by JPA
	@Override
	public void storeIncome(Income income) {
		manager.persist(income);
		System.out.println("CouponsDAOBean:\tAdded to DB : " + income);
	}

	// called by IncomeService to provide JPA access for JAX-RS
	@Override
	public List<Income> viewAllIncomes() {
		System.out.println("CouponsDAOBean:\tviewAllIncomes() called");
		return manager.createNamedQuery("viewAllIncomes", Income.class).getResultList();
	}

	// called by IncomeService to provide JPA access for JAX-RS
	@Override
	public List<Income> viewIncomeByCustomer(long customerId) {
		System.out.println("CouponsDAOBean:\tviewIncomeByCustomer called");
		return manager.createNamedQuery("viewIncomeByCustomer", Income.class)
				.setParameter("invokerId", customerId)
				.setParameter("incomeType", IncomeType.CUSTOMER_PURCHASE).getResultList();
	}

	// called by IncomeService to provide JPA access for JAX-RS
	@Override
	public List<Income> viewIncomeByCompany(long companyId) {
		System.out.println("CouponsDAOBean:\tviewIncomeByCompany called");
		return manager.createNamedQuery("viewIncomeByCompany", Income.class)
				.setParameter("invokerId", companyId)
				.setParameter("incomeType", IncomeType.CUSTOMER_PURCHASE).getResultList();
	}
}
