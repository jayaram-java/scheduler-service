package com.bank.scheduler.scheduler_service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bank.scheduler.scheduler_service.dto.LoginRequest;
import com.bank.scheduler.scheduler_service.dto.LoginResponse;
import com.bank.scheduler.scheduler_service.service.CustomerManagementFeignClient;
import com.bank.scheduler.scheduler_service.service.CustomerMgmtClient3;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CustomerManagementFeignClientImpl implements CustomerMgmtClient3 {

	private static final Logger logger = LogManager.getLogger(CustomerManagementFeignClientImpl.class);

	private final CustomerManagementFeignClient feignClient;

	public CustomerManagementFeignClientImpl(CustomerManagementFeignClient feignClient) {
		this.feignClient = feignClient;
	}

	private String getAuthToken() {

		logger.info("Calling authentication service via Feign...");

		LoginRequest request = new LoginRequest("raja", "123457");

		LoginResponse response = feignClient.login(request);

		if (response == null) {
			throw new RuntimeException("Auth response is null");
		}

		return response.getTokenType() + " " + response.getAccessToken();
	}

	@Override
	@CircuitBreaker(name = "customerService", fallbackMethod = "fallbackTransactions")
	public String getTransactionsByAccount(Long accountId) {

		logger.info("Fetching transactions via Feign for accountId: {}", accountId);

		String token = getAuthToken();

		return feignClient.getTransactions(token, accountId);
	}
	
	// Fallback Method
	public String fallbackTransactions(Long accountId, Exception ex) {

		logger.error("Customer service is down. Executing fallback. Reason: {}", ex.getMessage());

		return "Transactions currently unavailable for accountId: " + accountId + ". Please try again later.";
	}

}
