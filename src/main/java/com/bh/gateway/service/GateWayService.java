package com.bh.gateway.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bh.gateway.dto.AccountDetailsResponse;
import com.bh.gateway.dto.AddAccountRequest;
import com.bh.gateway.dto.CustomerDetailsResponse;
import com.bh.gateway.dto.Customers;
import com.bh.gateway.dto.TransactionDetailsResponse;
import com.bh.gateway.dto.TransactionRequest;
import com.bh.gateway.dto.Transactions;
import com.bh.gateway.model.Account;
import com.bh.gateway.model.Customer;
import com.bh.gateway.model.Transaction;
import com.bh.gatewy.constants.Constants;

@Service
public class GateWayService {

	private final Logger logger = LoggerFactory.getLogger(GateWayService.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private Environment environment;

	public Customers getCustomerIds() {
		logger.info("Start GateWayService.Customers() --> to fetch all the available Customer ids from the System");
		return restTemplate.getForObject(environment.getProperty("endpoint.account.customers"), Customers.class);
	}

	public CustomerDetailsResponse getCustomerDetails(String customerId) {
		logger.info("Start GateWayService.getCustomerDetails() --> to fetch Customer Details of selected Customer Id from the System");
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put(Constants.CUSTOMER_ID, customerId);
		Customer customer = restTemplate.getForObject(environment.getProperty("endpoint.account.customerById"),
				Customer.class, uriVariables);
		Transactions transactions = restTemplate.getForObject(
				environment.getProperty("endpoint.transaction.customerId"), Transactions.class, uriVariables);

		return getCustomerDetailsDTO(customer, transactions);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomerDetailsResponse addAccount(AddAccountRequest addAccountRequest) {
		logger.info("Start GateWayService.addAccount() --> to Add Secondary Current Account for the selected customer Id");
		HttpHeaders cheaders = new HttpHeaders();
		cheaders.set(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		cheaders.set(Constants.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity centity = new HttpEntity(addAccountRequest, cheaders);
		Customer customer = restTemplate.postForObject(environment.getProperty("endpoint.account.add"), centity,
				Customer.class);
		double initCred = Double.parseDouble(addAccountRequest.getInitialCredit());
		Transactions transactions = null;
		if (customer != null && customer.getErrorMessage() == null && initCred > 0) {
			TransactionRequest transactionRequest = new TransactionRequest();
			transactionRequest.setAccountType(Constants.ACCOUNT_TYPE_SECONDARY);
			transactionRequest.setCustomerId(addAccountRequest.getCustomerId());
			transactionRequest.setTransactionAmount(addAccountRequest.getInitialCredit());
			transactionRequest.setTransactionType(Constants.CUSTOMER_TRANSACTION_CREDIT);
			HttpHeaders headers = new HttpHeaders();
			headers.set(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			headers.set(Constants.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			HttpEntity entity = new HttpEntity(transactionRequest, headers);
			transactions = restTemplate.postForObject(environment.getProperty("endpoint.transaction.add"), entity,
					Transactions.class);
		} else if (customer != null && customer.getErrorMessage() == null) {
			logger.info("Start GateWayService.addAccount() --> Secondary Account Opened for the Customer but transaction is not logged as initial amount is = 0");
			Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put(Constants.CUSTOMER_ID, addAccountRequest.getCustomerId());
			transactions = restTemplate.getForObject(environment.getProperty("endpoint.transaction.customerId"),
					Transactions.class, uriVariables);
		} else if (customer != null && customer.getErrorMessage() != null) {
			logger.info("Start GateWayService.addAccount() --> "+customer.getErrorMessage());
			CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
			customerDetailsResponse.setErrorMessage(customer.getErrorMessage());
			return customerDetailsResponse;
		}
		return getCustomerDetailsDTO(customer, transactions);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomerDetailsResponse updateAccount(TransactionRequest transactionRequest) {
		logger.info("Start GateWayService.updateAccount() --> to Add new transaction for the selected customer Id");
		HttpHeaders cheaders = new HttpHeaders();
		cheaders.set(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		cheaders.set(Constants.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity centity = new HttpEntity(transactionRequest, cheaders);
		Customer customer = restTemplate.postForObject(environment.getProperty("endpoint.account.update"), centity,
				Customer.class);
		double transactionAmount = Double.parseDouble(transactionRequest.getTransactionAmount());
		Transactions transactions = null;
		if (customer != null && customer.getErrorMessage() == null && transactionAmount > 0) {
			HttpHeaders headers = new HttpHeaders();
			headers.set(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			headers.set(Constants.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			HttpEntity entity = new HttpEntity(transactionRequest, headers);
			transactions = restTemplate.postForObject(environment.getProperty("endpoint.transaction.add"), entity,
					Transactions.class);
		} else if (customer != null && customer.getErrorMessage() != null) {
			logger.info("Start GateWayService.updateAccount() --> Transaction is not logged as the value is not > 0");
			CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
			customerDetailsResponse.setErrorMessage(customer.getErrorMessage());
			return customerDetailsResponse;
		}
		return getCustomerDetailsDTO(customer, transactions);
	}

	private CustomerDetailsResponse getCustomerDetailsDTO(Customer customer, Transactions transactions) {
		logger.info("Start GateWayService.getCustomerDetailsDTO() --> Private method to transform model objects into DTO");
		CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
		customerDetailsResponse.setCustomerId(customer.getCustomerId());
		customerDetailsResponse.setName(customer.getName());
		customerDetailsResponse.setSurName(customer.getSurName());
		customerDetailsResponse.setErrorMessage(customer.getErrorMessage());
		List<AccountDetailsResponse> accountDetailsResponses = new ArrayList<>();
		for (Account account : customer.getAccounts()) {
			AccountDetailsResponse accountDetailsResponse = new AccountDetailsResponse();
			accountDetailsResponse.setAccountType(account.getAccountType());
			accountDetailsResponse.setBalance(account.getBalance());
			List<TransactionDetailsResponse> transactionsList = new ArrayList<TransactionDetailsResponse>();
			if (transactions != null) {
				for (Transaction transaction : transactions.getTransactions()) {
					if (transaction.getAccountType().equalsIgnoreCase(account.getAccountType())) {
						TransactionDetailsResponse transactiondetailsResponse = new TransactionDetailsResponse();
						transactiondetailsResponse.setTransactionAmount(transaction.getTransactionAmount());
						transactiondetailsResponse.setTransactionDate(transaction.getTransactionDate());
						transactiondetailsResponse.setTransactionDetail(transaction.getTransactionDetail());
						transactionsList.add(transactiondetailsResponse);
					}
				}
			}
			accountDetailsResponse.setTransactions(transactionsList);
			accountDetailsResponses.add(accountDetailsResponse);
		}
		customerDetailsResponse.setAccounts(accountDetailsResponses);
		return customerDetailsResponse;

	}

}
