package com.infiniteskills.data.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_ID")
	private Long accountId;

	@ManyToMany(cascade=CascadeType.ALL)// Source Entity
	@JoinTable(
					name="USER_ACCOUNT",// Join table name
					joinColumns=@JoinColumn(name="ACCOUNT_ID"),// Join table Source Entity Column
					inverseJoinColumns=@JoinColumn(name="USER_ID"))// Join table Target Entity Column
	private Set<User> users = new HashSet<>();// Collection of Target Entities

	@OneToMany(cascade=CascadeType.ALL, mappedBy="account")// Mapped by account in Transaction
//	@JoinColumn(name="ACCOUNT_ID", nullable=false)// The column name of the TRANSACTION table where is the FK of ACCOUNT. Has to be nullabe=false. Remove if it becomes a bidirectional relationship!!!
	List<Transaction> transactions = new ArrayList();

	@Column(name = "NAME")
	private String name;

	@Column(name = "INITIAL_BALANCE")
	private BigDecimal initialBalance;

	@Column(name = "CURRENT_BALANCE")
	private BigDecimal currentBalance;

	@Column(name = "OPEN_DATE")
	private Date openDate;

	@Column(name = "CLOSE_DATE")
	private Date closeDate;

	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(BigDecimal initialBalance) {
		this.initialBalance = initialBalance;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<User> getUsers() { return users;	}

	public void setUsers(Set<User> users) {	this.users = users;	}
}
