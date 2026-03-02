package com.bank.scheduler.scheduler_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.bank.scheduler.scheduler_service.dto.LoginRequest;
import com.bank.scheduler.scheduler_service.dto.LoginResponse;

@FeignClient(name = "customer-management-service")
public interface CustomerManagementFeignClient {

	@PostMapping("/customer-mgmt/api/auth/signin")
	LoginResponse login(@RequestBody LoginRequest request);

	@GetMapping("/customer-mgmt/api/transactions/account/{accountId}")
	String getTransactions(@RequestHeader("Authorization") String token, @PathVariable Long accountId);

}
