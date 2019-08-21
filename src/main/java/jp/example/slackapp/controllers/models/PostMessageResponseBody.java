package jp.example.slackapp.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PostMessageResponseBody {

	public String ok;
	
	public String error;
	
	public String channel;
	
	public String ts;
	
	public Message message;
	
	public PostMessageResponseBody() {
	}
	
	public PostMessageResponseBody(String ok, String error, String channel, String ts, Message message) {
		this.ok = ok;
		this.error = error;
		this.channel = channel;
		this.ts = ts;
		this.message = message;
	}

	@JsonIgnoreProperties(ignoreUnknown=true)
	public class Message {
	
		public String text;
		
		public String username;
		
		@JsonProperty("bot_id")
		public String botId;
		
		public String type;
		
		public String subtype;
		
		public String ts;
		
		public Message() {
		}
		
		public Message(String text, String username, String botId, String type, String subtype, String ts) {
			this.text = text;
			this.username = username;
			this.botId = botId;
			this.type = type;
			this.subtype = subtype;
			this.ts = ts;
		}
	}
}
