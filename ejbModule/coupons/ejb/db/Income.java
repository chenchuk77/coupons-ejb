package coupons.ejb.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import coupons.ejb.beans.IncomeType;

@Entity
@Table(name = "INCOME")
@NamedQueries({ @NamedQuery(name = "viewAllIncomes", query = "SELECT i From Income AS i ORDER BY i.date DESC"),
		@NamedQuery(name = "viewIncomeByCustomer", query = "SELECT i From Income AS i WHERE i.invokerId = :invokerId AND i.incomeType = :incomeType ORDER BY i.date DESC"),
		@NamedQuery(name = "viewIncomeByCompany", query = "SELECT i From Income AS i WHERE i.invokerId = :invokerId AND i.incomeType <> :incomeType ORDER BY i.date DESC") })
@XmlRootElement
public class Income implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private long invokerId;
	private Date date;
	private IncomeType incomeType;
	private double amount;

	public Income() {
		
	}

	public Income(long invokerId, IncomeType incomeType, double amount) {
		this.id = null;
		this.invokerId = invokerId;
		this.incomeType = incomeType;
		this.date = new Date();
		this.amount = amount;

	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getInvokerId() {
		return invokerId;
	}

	public void setInvokerId(long invokerId) {
		this.invokerId = invokerId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Enumerated(EnumType.STRING)
	public IncomeType getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(IncomeType incomeType) {
		this.incomeType = incomeType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Income [id=" + id + ", invokerId=" + invokerId + ", date=" + date + ", incomeType=" + incomeType
				+ ", amount=" + amount + "]";
	}

}
