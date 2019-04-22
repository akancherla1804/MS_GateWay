package com.bh.gateway.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailsResponse implements Serializable {
	private static final long serialVersionUID = -2447951959296520432L;
	public String transactionDate;
	public String transactionDetail;
	public double transactionAmount;

}
