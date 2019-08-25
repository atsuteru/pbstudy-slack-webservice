package jp.example.slackapp.controllers.contents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UploadFileResponseContent {

	public String ok;
	
	public String error;

	public String channel;
	
	public String ts;
	
	public Message message;

	@JsonIgnoreProperties(ignoreUnknown=true)
	public class Message {
	
		public String text;
		
		public String username;
		
		@JsonProperty("bot_id")
		public String botId;
		
		public String type;
		
		public String subtype;
		
		public String ts;
	}
}
