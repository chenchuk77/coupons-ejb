package coupons.ejb.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import coupons.ejb.dao.CouponsDAOLocal;
import coupons.ejb.db.Income;

@Stateless
public class IncomeServiceBean implements IncomeService , Serializable{
	private static final long serialVersionUID = 1L;
	private QueueConnectionFactory factory;
	private QueueConnection conn;
	private QueueSession session;
	private Queue Queue;
	private QueueSender queueSender;
	private ObjectMessage objectMessage;

	@EJB
	private CouponsDAOLocal couponsDAO;

	@PostConstruct
	public void init(){
		try {
			InitialContext ctx = new InitialContext();
			factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			conn = factory.createQueueConnection();
			session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue = (Queue) ctx.lookup("java:/jms/queue/DLQ");
			queueSender = session.createSender(Queue);
			objectMessage = session.createObjectMessage();
			System.out.println("IncomeServiceBean:\tinitialized.");
		} catch (JMSException | NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void storeIncome(Income income) {
		try {
			objectMessage.setObject(income);
			queueSender.send(objectMessage);
			System.out.println("IncomeServiceBean:\tnew income sent to queue.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void preDestroy() {
		try {
			queueSender.close();
		} catch (JMSException e) {
			System.out.println("IncomeServiceBean:\tEXCEPTION : " + e.getMessage());
		}
		try {
			session.close();
		} catch (JMSException e) {
			System.out.println("IncomeServiceBean:\tEXCEPTION : " + e.getMessage());
		}
		try {
			conn.close();
		} catch (JMSException e) {
			System.out.println("IncomeServiceBean:\tEXCEPTION : " + e.getMessage());
		}
		System.out.println("IncomeServiceBean:\tdestroyed successfuly.");
	}

	@Override
	public List<Income> viewAllIncomes() {
		System.out.println("IncomeServiceBean:\tviewAllIncomes() called");
		return couponsDAO.viewAllIncomes();
	}

	@Override
	public List<Income> viewIncomeByCustomer(long customerId) {
		System.out.println("IncomeServiceBean:\tviewIncomeByCustomer called for customer " + customerId);
		return couponsDAO.viewIncomeByCustomer(customerId);
	}

	@Override
	public List<Income> viewIncomeByCompany(long companyId) {
		System.out.println("IncomeServiceBean:\tviewIncomeByCompany called for company " + companyId);
		return couponsDAO.viewIncomeByCompany(companyId);
	}
}
