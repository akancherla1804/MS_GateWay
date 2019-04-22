package com.bh.gateway.dto;

import java.io.Serializable;
import java.util.List;

import com.bh.gateway.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions implements Serializable {

	private static final long serialVersionUID = 8233643476663287840L;

	private List<Transaction> transactions;

//	public Transactions() {
//
//	}
//
//	public Transactions(List<Transaction> transactions) {
//		super();
//		this.transactions = transactions;
//	}

}
