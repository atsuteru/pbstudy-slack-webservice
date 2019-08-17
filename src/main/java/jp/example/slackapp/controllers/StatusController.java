package jp.example.slackapp.controllers;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("status")
public class StatusController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getStatus() {
		return Map.of("status", true);
	}
}
