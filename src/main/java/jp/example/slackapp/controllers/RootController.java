package jp.example.slackapp.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RootController {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getWelcomeMessage() {
		return "Hello World!!";
	}
}
