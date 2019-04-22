/**
 * 
 */
package com.bh.gateway.model;

import java.io.Serializable;
import java.util.List;

public class Customer implements Serializable {

	private static final long serialVersionUID = -1183281095235708141L;

	public String customerId;
	public String name;
	public String surName;
	public List<Account> accounts;
	public String errorMessage;

	public Customer() {
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Customer(String customerId, String name, String surName, List<Account> accounts) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.surName = surName;
		this.accounts = accounts;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
