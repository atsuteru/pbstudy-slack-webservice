package jp.example.slackapp.controllers;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.example.slackapp.controllers.contents.EventEnableRequestContent;
import jp.example.slackapp.controllers.contents.EventEnableResponseContent;

@Path("event")
public class EventController {

	@Path("/enable")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EventEnableResponseContent receiveEnable(EventEnableRequestContent content) {
		
		if (!"url_verification".equalsIgnoreCase(content.type)) {
			var message = String.format("Type '%s' is not supported.", content.type);
			throw new BadRequestException(message);
		}

		return new EventEnableResponseContent() {{ challenge = content.challenge; }};
	}
}
