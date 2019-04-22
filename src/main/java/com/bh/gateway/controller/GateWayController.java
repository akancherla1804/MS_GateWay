package com.bh.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bh.gateway.dto.AddAccountRequest;
import com.bh.gateway.dto.CustomerDetailsResponse;
import com.bh.gateway.dto.Customers;
import com.bh.gateway.dto.TransactionRequest;
import com.bh.gateway.service.GateWayService;
import com.bh.gatewy.constants.Constants;

@RestController
public class GateWayController {

	@Autowired
	GateWayService gateWayService;

	@GetMapping(Constants.URI_GET_CUSTOMER_IDS)
	public Customers getCustomerIds() {
		return gateWayService.getCustomerIds();
	}

	@GetMapping(Constants.URI_GET_CUSTOMER_DETAILS)
	public CustomerDetailsResponse getCustomerDetails(@PathVariable String customerId) {
		return gateWayService.getCustomerDetails(customerId);
	}

	@PostMapping(Constants.URI_ADD_SECONDARY_ACCOUNT)
	public CustomerDetailsResponse addAccount(@RequestBody AddAccountRequest addAccountRequest) {
		return gateWayService.addAccount(addAccountRequest);
	}

	@PostMapping(Constants.URI_ADD_TRANSACTION)
	public CustomerDetailsResponse updateAccount(@RequestBody TransactionRequest transactionRequest) {
		return gateWayService.updateAccount(transactionRequest);
	}
}
