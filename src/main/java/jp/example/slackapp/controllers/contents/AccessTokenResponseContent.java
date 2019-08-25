package jp.example.slackapp.controllers.contents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccessTokenResponseContent {

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
}
