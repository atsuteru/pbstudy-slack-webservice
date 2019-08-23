package jp.example.slackapp.hooks;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

	@Override
	public Response toResponse(BadRequestException exception) {
		System.out.println(String.format("%s: %s", exception.getClass().getName(), exception.getMessage()));
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

}
