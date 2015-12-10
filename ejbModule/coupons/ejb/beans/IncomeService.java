package coupons.ejb.beans;

import java.util.List;
import javax.ejb.Remote;
import coupons.ejb.db.Income;

@Remote
public interface IncomeService {

	// async method to add income to a JMS quque
	public void storeIncome(Income income);

	// view methods, will respond immediately
	public List<Income> viewAllIncomes();
	public List<Income> viewIncomeByCustomer(long customerId);
	public List<Income> viewIncomeByCompany(long company);
}
