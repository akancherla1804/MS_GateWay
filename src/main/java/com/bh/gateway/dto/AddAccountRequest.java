package com.bh.gateway.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAccountRequest implements Serializable {

	private static final long serialVersionUID = -5070728149905703649L;
	private String customerId;
	private String initialCredit;

}
