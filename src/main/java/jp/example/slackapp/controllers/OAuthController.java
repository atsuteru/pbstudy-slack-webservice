package jp.example.slackapp.controllers;

import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("oauth")
public class OAuthController {

	@Path("/redirect")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> receiveRedirect(@QueryParam("code") String code, @QueryParam("state") String state) {
		ResourceBundle res = ResourceBundle.getBundle("slack");
		var clientId = res.getString("clientId");
		var clientSecret = res.getString("clientSecret");
		return Map.of(
				"code", code, 
				"state", state, 
				"client_id", clientId);
	}
}
