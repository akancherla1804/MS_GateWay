package com.bh.gateway.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customers implements Serializable {

	private static final long serialVersionUID = 4507088815341804389L;
	private Set<String> customerIds;

}
