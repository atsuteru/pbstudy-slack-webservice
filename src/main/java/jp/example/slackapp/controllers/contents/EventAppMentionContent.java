package jp.example.slackapp.controllers.contents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventAppMentionContent {

	public String token;

	@JsonProperty("team_id")
	public String teamId;

	@JsonProperty("api_app_id")
	public String apiAppId;
	
	public Event event;
	
	public String type;
	
	@JsonProperty("event_id")
	public String eventId;
	
	@JsonProperty("event_time")
	public String eventTime;
	
	@JsonProperty("authed_users")
	public String[] authedUsers;

	@JsonIgnoreProperties(ignoreUnknown=true)
	public class Event {

		public String client_msg_id;

		public String type;
		
		public String text;

		public String user;
		
		public String ts;
		
		public String channel;
		
		@JsonProperty("event_ts")
		public String eventTs;
	}
}
