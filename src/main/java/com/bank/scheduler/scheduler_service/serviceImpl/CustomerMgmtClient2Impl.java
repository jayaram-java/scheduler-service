package com.bank.scheduler.scheduler_service.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.bank.scheduler.scheduler_service.dto.LoginRequest;
import com.bank.scheduler.scheduler_service.dto.LoginResponse;
import com.bank.scheduler.scheduler_service.service.CustomerMgmtClient2;

@Service
public class CustomerMgmtClient2Impl implements CustomerMgmtClient2 {

	private static final Logger logger = LogManager.getLogger(CustomerMgmtClient2Impl.class);

	private final RestClient restClient;

	private static final String BASE_URL = "http://localhost:8084/customer-mgmt/api";

	public CustomerMgmtClient2Impl(RestClient restClient) {
		this.restClient = restClient;
	}

	private String getAuthToken() {

		logger.info("Calling authentication service...");

		String loginUrl = BASE_URL + "/auth/signin";

		LoginRequest loginRequest = new LoginRequest("raja", "123457");

		try {

			logger.debug("Sending auth request to URL: {}", loginUrl);

			LoginResponse response = restClient.post().uri(loginUrl).body(loginRequest).retrieve()
					.body(LoginResponse.class);

			if (response == null) {
				logger.error("Authentication response is null");
				throw new RuntimeException("Auth response is null");
			}

			logger.info("Authentication successful. Token received.");

			// ⚠ DO NOT log full token in production
			logger.debug("Token Type: {}", response.getTokenType());

			return response.getTokenType() + " " + response.getAccessToken();

		} catch (Exception e) {
			logger.error("Error while authenticating user", e);
			throw e;
		}
	}

	private HttpHeaders createAuthHeaders() {

		logger.debug("Creating authorization headers...");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(HttpHeaders.AUTHORIZATION, getAuthToken());

		logger.debug("Authorization headers created successfully");

		return headers;
	}

	@Override
	public String getTransactionsByAccount(Long accountId) {

		logger.info("Fetching transactions for accountId: {}", accountId);

		String url = BASE_URL + "/transactions/account/{accountId}";

		try {

			String response = restClient.get().uri(url, accountId)
					.headers(headers -> headers.addAll(createAuthHeaders())).retrieve().body(String.class);

			logger.info("Transactions fetched successfully for accountId: {}", accountId);

			logger.debug("Transaction response: {}", response);

			return response;

		} catch (Exception e) {
			logger.error("Error while fetching transactions for accountId: {}", accountId, e);
			throw e;
		}
	}

}
