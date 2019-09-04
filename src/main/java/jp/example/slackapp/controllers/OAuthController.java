package jp.example.slackapp.controllers;

import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.example.slackapp.SlackClient;

@Path("oauth")
public class OAuthController {

	@Path("/redirect")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> receiveRedirect(@QueryParam("code") String code, @QueryParam("state") String state) {
		
		// Collect parameters
		var res = ResourceBundle.getBundle("slack");
		var clientId = res.getString("clientId");
		var clientSecret = res.getString("clientSecret");
		var postChannel = res.getString("oauth.postChannel");
		
		// Get Access token
		var authResponse = SlackClient.getAccessToken(clientId, clientSecret, code);
		if (!"true".equalsIgnoreCase(authResponse.ok)) {
			return Map.of(
					"code", code, 
					"state", state, 
					"client_id", clientId,
					"auth_result", authResponse);
		}
		
		// Set Access token to this service
		String grantedMessage;
		if (authResponse.bot != null) {
			SlackClient.SetAccessToken(authResponse.bot.botAccessToken);
			grantedMessage = "Access token has been granted. (Bot mode)";
		} else {
			SlackClient.SetAccessToken(authResponse.accessToken);
			grantedMessage = "Access token has been granted. (App mode)";
		}
				
		// POST Message
		var postResponse = SlackClient.postMessage(postChannel, grantedMessage);

		return Map.of(
				"code", code, 
				"state", state, 
				"client_id", clientId,
				"auth_result", authResponse,
				"post_result", postResponse);
	}
}
