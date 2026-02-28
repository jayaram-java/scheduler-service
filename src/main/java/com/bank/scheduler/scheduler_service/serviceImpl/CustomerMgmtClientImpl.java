package com.bank.scheduler.scheduler_service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.bank.scheduler.scheduler_service.dto.LoginRequest;
import com.bank.scheduler.scheduler_service.dto.LoginResponse;
import com.bank.scheduler.scheduler_service.service.CustomerMgmtClient;

@Service
public class CustomerMgmtClientImpl implements CustomerMgmtClient {

	private static final Logger logger = LogManager.getLogger(CustomerMgmtClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	private final String BASE_URL = "http://localhost:8084/customer-mgmt/api";

	private String getAuthToken() {

		logger.info("*****getAuthToken");

		String loginUrl = BASE_URL + "/auth/signin";

		LoginRequest loginRequest = new LoginRequest("raja", "123457");

		ResponseEntity<LoginResponse> response = restTemplate.postForEntity(loginUrl, loginRequest,
				LoginResponse.class);

		LoginResponse body = response.getBody();

		System.out.println(response.getBody());

		return body.getTokenType() + " " + body.getAccessToken();
	}

	private HttpHeaders createAuthHeaders() {

		HttpHeaders headers = new HttpHeaders();
		try {
			logger.debug("createAuthHeaders");
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(HttpHeaders.AUTHORIZATION, getAuthToken());
		} catch (Exception e) {
			logger.error("Error occurred while generating statement", e);
		}

		return headers;
	}

	public String getTransactionsByAccount(Long accountId) {

		String url = BASE_URL + "/transactions/account/{accountId}";

		HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, accountId);

		return response.getBody();
	}
}
