package jp.example.slackapp.controllers.contents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventEnableResponseContent {

	public String token;
	
	public String challenge;

	public String type;
}
