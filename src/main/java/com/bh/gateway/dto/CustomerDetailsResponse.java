package com.bh.gateway.dto;

import java.io.Serializable;
import java.util.List;

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
public class CustomerDetailsResponse implements Serializable {
	
	private static final long serialVersionUID = 2744672605420389795L;
	public String customerId;
	public String name;
	public String surName;
	public List<AccountDetailsResponse> accounts;
	public String errorMessage;


}
