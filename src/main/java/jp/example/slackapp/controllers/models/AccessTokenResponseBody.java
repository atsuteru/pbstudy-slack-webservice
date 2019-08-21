package jp.example.slackapp.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccessTokenResponseBody {

	public String ok;
	
	public String error;

	@JsonProperty("access_token")
	public String accessToken;
	
	public String scope;
	
	@JsonProperty("user_id")
	public String userId;
	
	public String teamName;
	
	@JsonProperty("team_id")
	public String teamId;

	public AccessTokenResponseBody() {
	}

	public AccessTokenResponseBody(String ok, String error, String accessToken, String scope, String userId, String teamName,
			String teamId) {
		this.ok = ok;
		this.error = error;
		this.accessToken = accessToken;
		this.scope = scope;
		this.userId = userId;
		this.teamName = teamName;
		this.teamId = teamId;
	}
}
