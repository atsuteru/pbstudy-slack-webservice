package jp.example.slackapp;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import jp.example.slackapp.controllers.contents.AccessTokenResponseContent;
import jp.example.slackapp.controllers.contents.PostMessageResponseContent;
import jp.example.slackapp.utils.JsonObject;

public class SlackClient {

	protected static final WebTarget SLACK = ClientBuilder.newClient().target("https://slack.com");

	private static String _accessToken = "";
	
	public static AccessTokenResponseContent getAccessToken(String clientId, String clientSecret, String code) {
		AccessTokenResponseContent authResponse;

		String authResponseString;
		try {
			authResponseString = SLACK.path("/api/oauth.access")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret)
					.queryParam("code", code)
					.request().accept("application/json")
					.get(String.class);
		} catch (Exception e) {
			authResponseString = "Request Error:" + e.getMessage();
			authResponse = new AccessTokenResponseContent();
			authResponse.ok = "false";
			authResponse.error = authResponseString;
			return authResponse;
		}
		
		try {
			authResponse = JsonObject.from(authResponseString);
		} catch (RuntimeException e) {
			authResponseString = "Parse Error:" + e.getMessage() + ", Source:" + authResponseString;
			authResponse = new AccessTokenResponseContent();
			authResponse.ok = "false";
			authResponse.error = authResponseString;
			return authResponse;
		}
		
		if (!"true".equalsIgnoreCase(authResponse.ok)) {
			System.out.println(String.format(
					"Get AccessToken was failed!! cause='%s'", authResponseString));
		}

		return authResponse;
	}
	
	public static void SetAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	public static PostMessageResponseContent postMessage(String postChannel, String text) {
		return postMessage(postChannel, text, false);
	}

	public static PostMessageResponseContent postMessageAsUser(String postChannel, String text) {
		return postMessage(postChannel, text, true);
	}

	protected static PostMessageResponseContent postMessage(String postChannel, String text, boolean asUser) {
		PostMessageResponseContent postResponse;
		
		String postResponseString;
		try {
			postResponseString = SLACK.path("/api/chat.postMessage")
					.queryParam("token", _accessToken)
					.queryParam("channel", postChannel)
					.queryParam("text", text)
					.queryParam("as_user", asUser)
					.request().accept("application/json")
					.get(String.class);
		} catch (Exception e) {
			postResponseString = "Parse Error:" + e.getMessage();
			postResponse = new PostMessageResponseContent();
			postResponse.ok = "false";
			postResponse.error = "Request Error:" + e.getMessage();
			return postResponse;
		}

		try {
			postResponse = JsonObject.from(postResponseString);
		} catch (Exception e) {
			postResponseString = "Parse Error:" + e.getMessage() + ", Source:" + postResponseString;
			postResponse = new PostMessageResponseContent();
			postResponse.ok = "false";
			postResponse.error = postResponseString;
			return postResponse;
		}
		
		if (!"true".equalsIgnoreCase(postResponse.ok)) {
			System.out.println(String.format(
					"Post Message was failed!! cause='%s'", postResponseString));
		}
		
		return postResponse;
	}
}
