package jp.example.businesscard;

import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import jp.example.businesscard.jaxrs.contents.BusinessCardRequest;
import jp.example.businesscard.jaxrs.contents.BusinessCardResponse;

public class BusinessCardGeneratorClient {

	protected static final WebTarget BUSINESS_CARD_GENERATOR = 
			ClientBuilder.newClient().target("https://business-card-webservice.herokuapp.com");
	
	public static byte[] generate(String templateName, Map<String, String> parameters) {
		BusinessCardRequest request = new BusinessCardRequest();
		request.templateName = templateName;
		request.parameters = parameters;
		
		BusinessCardResponse response;
		try {
			response = BUSINESS_CARD_GENERATOR.path("/api/businesscard/generate")
					.request()
					.post(Entity.json(request), BusinessCardResponse.class);
		} catch (Exception e) {
			throw new RuntimeException("business-card-webservice service error", e);
		}
		
		if (!response.ok) {
			throw new RuntimeException("business-card-webservice returned with an error.", new Exception(response.error));
		}
		
		return response.pdf;
	}
}
