package com.ofg.deposits.products.safetydepositbox.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ofg.deposits.products.safetydepositbox.exceptions.DepositBoxNotFoundException;
import com.ofg.deposits.products.safetydepositbox.exceptions.DuplicateOwnerException;
import com.ofg.deposits.products.safetydepositbox.exceptions.OwnerNotAuthorizedException;
import com.ofg.deposits.products.safetydepositbox.model.GetOwnerResponse;
import com.ofg.deposits.products.safetydepositbox.model.NewBoxOwner;
import com.ofg.deposits.products.safetydepositbox.model.UpdateOwnerResponse;
import com.ofg.deposits.products.safetydepositbox.service.DepositBoxService;

@Validated
@RestController
public class SafetyDepositBoxController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SafetyDepositBoxController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DepositBoxService depositBoxService;

	@GetMapping("testspring")
	public String getOwnerDetails() {

		return "simple Spring controller call works";

	}

}
