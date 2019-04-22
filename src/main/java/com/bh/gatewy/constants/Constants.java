package com.bh.gatewy.constants;

public class Constants {

	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT = "Accept";
	public static final String CUSTOMER_ID = "customerId";
	public static final String ACCOUNT_TYPE_SECONDARY = "Secondary";
	public static final String CUSTOMER_TRANSACTION_CREDIT = "Credit";

	public static final String URI_GET_CUSTOMER_IDS = "/account/customers";
	public static final String URI_GET_CUSTOMER_DETAILS = "/account/{customerId}";
	public static final String URI_ADD_SECONDARY_ACCOUNT = "/customer/addAccount";
	public static final String URI_ADD_TRANSACTION = "/customer/transaction";
}
