package com.bank.scheduler.scheduler_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.scheduler.scheduler_service.service.CustomerMgmtClient2;

@RestController
@RequestMapping("/external/v2")
public class Customermgmt2Controller {

	private final CustomerMgmtClient2 customerMgmtClient2;

	public Customermgmt2Controller(CustomerMgmtClient2 customerMgmtClient2) {

		this.customerMgmtClient2 = customerMgmtClient2;
	}

	@GetMapping("/transactions/{accountId}")
	public ResponseEntity<String> getTransactions(@PathVariable Long accountId) {

		String response = customerMgmtClient2.getTransactionsByAccount(accountId);

		return ResponseEntity.ok(response);
	}
}
