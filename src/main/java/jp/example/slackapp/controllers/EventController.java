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

import jp.example.slackapp.controllers.contents.EventEnableRequestContent;
import jp.example.slackapp.controllers.contents.EventEnableResponseContent;
import jp.example.slackapp.utils.JsonObject;

@Path("event")
public class EventController {

	protected Map<String, Function<String, EventEnableResponseContent>> _consumers = new HashMap<String, Function<String, EventEnableResponseContent>>();
	
	public EventController(){
		GenerateActions(_consumers);
	}
	
	protected void GenerateActions(Map<String, Function<String, EventEnableResponseContent>> consumers) {
		_consumers.put("url_verification", (jsonContent) -> {return receiveEnable(JsonObject.from(jsonContent));});
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
		
		if (!_consumers.containsKey(content.type)) {
			throw new BadRequestException(String.format("Unsupported type: '%s'", jsonContent));
		}
		
		return _consumers.get(content.type).apply(jsonContent);
	}

	protected EventEnableResponseContent receiveEnable(EventEnableRequestContent content) {
		
		if (!"url_verification".equalsIgnoreCase(content.type)) {
			var message = String.format("Type '%s' is not supported.", content.type);
			throw new BadRequestException(message);
		}

		return new EventEnableResponseContent() {{ challenge = content.challenge; }};
	}
}
