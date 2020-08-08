package com.ofg.deposits.products.safetydepositbox.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
