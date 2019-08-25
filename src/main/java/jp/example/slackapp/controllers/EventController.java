package jp.example.slackapp.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.example.slackapp.controllers.contents.EventAppMentionContent;
import jp.example.slackapp.controllers.contents.EventCallbackRequestContent;
import jp.example.slackapp.controllers.contents.EventEnableRequestContent;
import jp.example.slackapp.controllers.contents.EventEnableResponseContent;
import jp.example.slackapp.utils.JsonObject;

@Path("event")
public class EventController {

	protected Map<String, Function<String, EventEnableResponseContent>> _actions = new HashMap<String, Function<String, EventEnableResponseContent>>();
	
	public EventController(){
		GenerateActions(_actions);
	}
	
	protected void GenerateActions(Map<String, Function<String, EventEnableResponseContent>> consumers) {
		_actions.put("url_verification", (jsonContent) -> {return receiveEnable(JsonObject.from(jsonContent));});
		_actions.put("event_callback", (jsonContent) -> {return receiveCallback(JsonObject.from(jsonContent), jsonContent);});

		_actions.put("app_mention", (jsonContent) -> {return receiveAppMention(JsonObject.from(jsonContent));});
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EventEnableResponseContent receive(String jsonContent) {

		EventEnableRequestContent content;

		try {
			content = JsonObject.from(jsonContent);
		} catch (RuntimeException e) {
			throw new BadRequestException(String.format("Unsupported Json format: '%s'", jsonContent), e);
		}
		
		if (!_actions.containsKey(content.type)) {
			throw new BadRequestException(String.format("Unsupported type: '%s'", jsonContent));
		}
		
		return _actions.get(content.type).apply(jsonContent);
	}

	protected EventEnableResponseContent receiveEnable(EventEnableRequestContent content) {
		return new EventEnableResponseContent() {{ challenge = content.challenge; }};
	}

	protected EventEnableResponseContent receiveCallback(EventCallbackRequestContent content, String jsonContent) {
		if (content.event == null) {
			throw new BadRequestException(String.format("Unsupported event because 'event' is not defined: '%s'", jsonContent));
		}

		if (!_actions.containsKey(content.event.type)) {
			throw new BadRequestException(String.format("Unsupported type: '%s'", jsonContent));
		}
		
		return _actions.get(content.event.type).apply(jsonContent);
	}

	protected EventEnableResponseContent receiveAppMention(EventAppMentionContent content) {
		System.out.println(String.format("Receive App menthion '%s' from user '%s' in channel'%s'", content.event.text, content.event.user, content.event.channel));
		return new EventEnableResponseContent();
	}
}
