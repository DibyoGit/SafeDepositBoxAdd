package com.ofg.deposits.products.safetydepositbox.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ofg.deposits.products.safetydepositbox.model.UpdateOwnerResponse;
import com.ofg.deposits.products.safetydepositbox.exceptions.DepositBoxNotFoundException;
import com.ofg.deposits.products.safetydepositbox.exceptions.DuplicateOwnerException;
import com.ofg.deposits.products.safetydepositbox.exceptions.OwnerNotAuthorizedException;
import com.ofg.deposits.products.safetydepositbox.model.GetOwnerResponse;

@Service
public class DepositBoxService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepositBoxService.class);
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public UpdateOwnerResponse updateBoxOwnerData(String brnch, String boxPrfx, String boxNbr) {
		// final String fisRetirementPlansURL =
		// "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/1/boxes/KRJ/260/owners";
		final String fisBoxURL = "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/" + brnch + "/boxes/"
				+ boxPrfx + "/" + boxNbr + "/owners";
		ResponseEntity<UpdateOwnerResponse> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("Brnch", brnch);
		params.put("BoxPrfx", boxPrfx);
		params.put("BoxNbr", boxNbr);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("organization-id", "BA-455");
		headers.set("source-id", "X3ANY0");
		headers.set("application-id", "CI");
		headers.set("uuid", java.util.UUID.randomUUID().toString());
		headers.set("security-token-type", "Oauth2");
		// headers.set("IBS-Authorization", "ZTQ1NTYyNDpHYW5nZXMkNA==");
		headers.set("Authorization", "Bearer 403ed551-8413-32ad-bd38-bfa4d1062b9d");
		HttpEntity<String> headerObject = new HttpEntity<String>("parameters", headers);

		LOGGER.info("PEGA has requested these box owner combo to be created. Branch ->"+brnch + "::BoxPrefix ->"+boxPrfx + "::BoxNumber ->"+boxNbr);
		try {
			GetOwnerResponse getOwnerRecordObject = getBoxOwnerData(brnch, boxPrfx, boxNbr);
			LOGGER.info("Validating the values PEGA has requested : Branch->"
					+ getOwnerRecordObject.getEntity().getBoxdata().getBrnch() + "::BoxPrefix ->"
					+ getOwnerRecordObject.getEntity().getBoxdata().getBoxPrfx() + "::BoxNumber ->"
					+ getOwnerRecordObject.getEntity().getBoxdata().getBoxNbr());

			if (getOwnerRecordObject.getEntity().getBoxdata().getBrnch() == null) {
				responseEntity = restTemplate.exchange(fisBoxURL, HttpMethod.PUT, headerObject,
						UpdateOwnerResponse.class, params);
				return responseEntity.getBody();
			} else if (getOwnerRecordObject.getEntity().getBoxdata().getBrnch() != null) {
				throw new DuplicateOwnerException(String.format(
						"Owner already exist based on branch/boxPrefix and boxNumber combo. You are not authorized to add a duplicate record"));

			}

		} catch (HttpClientErrorException e) {
			throw new OwnerNotAuthorizedException(
					String.format("Record provided is not fit for creating a record for Onwer"));
		}
		return null;
	}

	public GetOwnerResponse getBoxOwnerData(String Brnch, String BoxPrfx, String BoxNbr) {

		// final String fisURL =
		// "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/2202/boxes/3/78/owner-defaults";
		// final String fisURL =
		// "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/" +Brnch+ "/boxes/"
		// +BoxPrfx+ "/" +BoxNbr+ "/owners";
		final String fisURL = "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/" + Brnch + "/boxes/" + BoxPrfx
				+ "/" + BoxNbr + "/owner-defaults";
		// String fisURL = "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/"
		// +Brnch+ "/boxes/" +BoxPrfx+ "/" +BoxNbr;
		ResponseEntity<GetOwnerResponse> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("Brnch", Brnch);
		params.put("BoxPrfx", BoxPrfx);
		params.put("BoxNbr", BoxNbr);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("organization-id", "BA-455");
		headers.set("source-id", "X3ANY0");
		headers.set("application-id", "CI");
		headers.set("uuid", java.util.UUID.randomUUID().toString());
		headers.set("security-token-type", "Oauth2");
		// headers.set("IBS-Authorization", "ZTQ1NTYyNDpHYW5nZXMkNA==");
		headers.set("Authorization", "Bearer 403ed551-8413-32ad-bd38-bfa4d1062b9d");
		HttpEntity<String> headerObject = new HttpEntity<String>("parameters", headers);

		try {
			responseEntity = restTemplate.exchange(fisURL, HttpMethod.GET, headerObject, GetOwnerResponse.class,
					params);
		} catch (HttpClientErrorException e) {
			throw new DepositBoxNotFoundException(
					String.format("Branch/BoxPrefix/Box Number combination yields no DepositBox data"));
		}
		return responseEntity.getBody();
	}

	public UpdateOwnerResponse createBoxOwnerData(String Brnch, String BoxPrfx, String BoxNbr) {
		// final String fisRetirementPlansURL =
		// "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/1/boxes/KRJ/260/owners";
		final String fisBoxURL = "https://api-gw-uat.fisglobal.com/rest/IBSSB/v2/branches/" + Brnch + "/boxes/"
				+ BoxPrfx + "/" + BoxNbr + "/owners";
		ResponseEntity<UpdateOwnerResponse> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("Brnch", Brnch);
		params.put("BoxPrfx", BoxPrfx);
		params.put("BoxNbr", BoxNbr);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("organization-id", "BA-455");
		headers.set("source-id", "X3ANY0");
		headers.set("application-id", "CI");
		headers.set("uuid", java.util.UUID.randomUUID().toString());
		headers.set("security-token-type", "Oauth2");
		// headers.set("IBS-Authorization", "ZTQ1NTYyNDpHYW5nZXMkNA==");
		headers.set("Authorization", "Bearer 403ed551-8413-32ad-bd38-bfa4d1062b9d");
		HttpEntity<String> headerObject = new HttpEntity<String>("parameters", headers);

		try {
			responseEntity = restTemplate.exchange(fisBoxURL, HttpMethod.POST, headerObject, UpdateOwnerResponse.class,
					params);
		} catch (HttpClientErrorException e) {
			throw new DepositBoxNotFoundException(
					String.format("Branch/BoxPrefix/Box Number combination yields no DepositBox data"));
		}
		return responseEntity.getBody();
	}
}
