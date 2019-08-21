package jp.example.slackapp.controllers;

import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.example.slackapp.controllers.models.AccessTokenResponseBody;
import jp.example.slackapp.controllers.models.PostMessageResponseBody;

@Path("oauth")
public class OAuthController {

	@Path("/redirect")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> receiveRedirect(@QueryParam("code") String code, @QueryParam("state") String state) {
		
		// Collect parameters
		ResourceBundle res = ResourceBundle.getBundle("slack");
		var clientId = res.getString("clientId");
		var clientSecret = res.getString("clientSecret");
		
		// Get Access token
		String authResponseString;
		try {
			authResponseString = ClientBuilder.newClient()
					.target("https://slack.com").path("/api/oauth.access")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret)
					.queryParam("code", code)
					.request().accept("application/json")
					.get(String.class);
		} catch (Exception e) {
			return Map.of(
					"code", code, 
					"state", state, 
					"client_id", clientId,
					"client_secret", clientSecret,
					"auth_result_str", "ERROR:" + e.getMessage());
		}
		AccessTokenResponseBody authResponse;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			authResponse = objectMapper.readValue(authResponseString,  AccessTokenResponseBody.class);
		} catch (Exception e) {
			return Map.of(
					"code", code, 
					"state", state, 
					"client_id", clientId,
					"client_secret", clientSecret,
					"auth_result_str", authResponseString,
					"auth_result", "ERROR:" + e.getMessage());
		}

		// POST Message
		String postResponseString;
		try {
			postResponseString = ClientBuilder.newClient()
					.target("https://slack.com").path("/api/chat.postMessage")
					.queryParam("token", authResponse.accessToken)
					.queryParam("channel", "CM34B0KMZ")
					.queryParam("text", "Access token has been granted.")
					.request().accept("application/json")
					.get(String.class);
		} catch (Exception e) {
			return Map.of(
					"code", code, 
					"state", state, 
					"client_id", clientId,
					"client_secret", clientSecret,
					"auth_result_str", authResponseString,
					"auth_result", authResponse,
					"post_restlt_str", "ERROR:" + e.getMessage());
		}
		PostMessageResponseBody postResponse;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			postResponse = objectMapper.readValue(postResponseString,  PostMessageResponseBody.class);
		} catch (Exception e) {
			return Map.of(
					"code", code, 
					"state", state, 
					"client_id", clientId,
					"client_secret", clientSecret,
					"auth_result_str", authResponseString,
					"auth_result", authResponse,
					"post_restlt_str", postResponseString,
					"post_restlt", "ERROR:" + e.getMessage());
		}

		return Map.of(
				"code", code, 
				"state", state, 
				"client_id", clientId,
				"client_secret", clientSecret,
				"auth_result_str", authResponseString,
				"auth_result", authResponse,
				"post_restlt_str", postResponseString,
				"post_restlt", postResponse);
	}
}
