package com.bh.gateway.model;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 9175598266409620208L;

	public double balance;
	public String accountType;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
