package jp.example.slackapp.controllers;

import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import jp.example.slackapp.controllers.contents.AccessTokenResponseContent;
import jp.example.slackapp.controllers.contents.PostMessageResponseContent;
import jp.example.slackapp.utils.JsonObject;

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
		var postChannel = res.getString("oauth.postChannel");
		
		// Get Access token
		var authResponse = getAccessToken(clientId, clientSecret, code);

		// POST Message
		var postResponse = postMessage(authResponse.accessToken, postChannel, "Access token has been granted.");

		return Map.of(
				"code", code, 
				"state", state, 
				"client_id", clientId,
				"auth_result", authResponse,
				"post_result", postResponse);
	}

	protected AccessTokenResponseContent getAccessToken(String clientId, String clientSecret, String code) {
		AccessTokenResponseContent authResponse;

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
			authResponse = new AccessTokenResponseContent();
			authResponse.error = "Request Error:" + e.getMessage();
			return authResponse;
		}
		
		try {
			authResponse = JsonObject.from(authResponseString);
		} catch (RuntimeException e) {
			authResponse = new AccessTokenResponseContent();
			authResponse.error = "Parse Error:" + e.getMessage();
			authResponse.source = authResponseString;
			return authResponse;
		}
		
		return authResponse;
	}

	protected PostMessageResponseContent postMessage(String accessToken, String postChannel, String text) {
		PostMessageResponseContent postResponse;
		
		String postResponseString;
		try {
			postResponseString = ClientBuilder.newClient()
					.target("https://slack.com").path("/api/chat.postMessage")
					.queryParam("token", accessToken)
					.queryParam("channel", postChannel)
					.queryParam("text", "Access token has been granted.")
					.request().accept("application/json")
					.get(String.class);
		} catch (Exception e) {
			postResponse = new PostMessageResponseContent();
			postResponse.error = "Request Error:" + e.getMessage();
			return postResponse;
		}

		try {
			postResponse = JsonObject.from(postResponseString);
		} catch (Exception e) {
			postResponse = new PostMessageResponseContent();
			postResponse.error = "Parse Error:" + e.getMessage();
			postResponse.source = postResponseString;
			return postResponse;
		}
		
		return postResponse;
	}
}
