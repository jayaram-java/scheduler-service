package com.bank.scheduler.scheduler_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.scheduler.scheduler_service.service.CustomerMgmtClient3;

@RestController
@RequestMapping("/external/v3")
public class Customermgmt3Controller {

	private final CustomerMgmtClient3 customerMgmtClient3;

	public Customermgmt3Controller(CustomerMgmtClient3 customerMgmtClient3) {
		this.customerMgmtClient3 = customerMgmtClient3;
	}

	@GetMapping("/transactions/{accountId}")
	public ResponseEntity<String> getTransactions(@PathVariable Long accountId) {

		String response = customerMgmtClient3.getTransactionsByAccount(accountId);

		return ResponseEntity.ok(response);
	}

}
