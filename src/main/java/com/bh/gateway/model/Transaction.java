package com.bh.gateway.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Transaction implements Serializable {

	private static final long serialVersionUID = -6319016444864599904L;

	private String transactionDate;
	private String transactionDetail;
	private double transactionAmount;
	private String customerId;
	private String accountType;

}
