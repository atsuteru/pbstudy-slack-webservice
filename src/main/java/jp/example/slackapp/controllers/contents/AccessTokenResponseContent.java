package jp.example.slackapp.controllers.contents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccessTokenResponseContent {

	public String ok;
	
	public String error;

	public String source;

	@JsonProperty("access_token")
	public String accessToken;
	
	public String scope;
	
	@JsonProperty("user_id")
	public String userId;
	
	@JsonProperty("team_id")
	public String teamId;
	
	@JsonProperty("team_name")
	public String teamName;

	public Bot bot;

	@JsonIgnoreProperties(ignoreUnknown=true)
	public class Bot {
	
		@JsonProperty("bot_user_id")
		public String botUserId;
		
		@JsonProperty("bot_access_token")
		public String botAccessToken;
	}

}
