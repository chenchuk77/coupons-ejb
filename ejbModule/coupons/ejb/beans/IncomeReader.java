package coupons.ejb.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import coupons.ejb.dao.CouponsDAOLocal;
import coupons.ejb.db.Income;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/DLQ") })
public class IncomeReader implements MessageListener {

	@EJB
	private CouponsDAOLocal couponsDAO;

	@Override
	// get called on this MDB when a message exist on the queue
	//
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage obj = (ObjectMessage) message;
			try {
				Income income = (Income) obj.getObject();
				System.out.println("IncomeReader:\treceived new income : " + income);
				couponsDAO.storeIncome(income);
				System.out.println("IncomeReader:\tadded new income # " + income.getId());
			} catch (JMSException e) {
				System.out.println("IncomeReader:\tERROR:\tJMSException : " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("IncomeReader:\tERROR:\tnot instance of ObjectMessage");
		}
	}

	@PostConstruct
	public void init() {
		System.out.println("IncomeReader:\tcreated.");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("IncomeReader:\tdestroyed.");
	}
}
