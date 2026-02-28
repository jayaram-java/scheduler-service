package com.bank.scheduler.scheduler_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.scheduler.scheduler_service.service.CustomerMgmtClient;

@RestController
@RequestMapping("/external/v1")
public class CustomermgmtController {

	private final CustomerMgmtClient customerMgmtClient;

	public CustomermgmtController(CustomerMgmtClient customerMgmtClient) {
		this.customerMgmtClient = customerMgmtClient;
	}

	@GetMapping("/transactions/{accountId}")
	public ResponseEntity<String> getTransactions(@PathVariable Long accountId) {

		String response = customerMgmtClient.getTransactionsByAccount(accountId);

		return ResponseEntity.ok(response);
	}

}
