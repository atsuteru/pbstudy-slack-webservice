package jp.example.slackapp;

import java.io.InputStream;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import jp.example.slackapp.controllers.contents.AccessTokenResponseContent;
import jp.example.slackapp.controllers.contents.PostMessageResponseContent;
import jp.example.slackapp.controllers.contents.UploadFileResponseContent;
import jp.example.slackapp.utils.JsonObject;

public class SlackClient {

	protected static final WebTarget SLACK = 
			ClientBuilder.newClient().target("https://slack.com");

	protected static final WebTarget SLACK_UPLOAD = 
			ClientBuilder.newBuilder().register(MultiPartFeature.class).build().target("https://slack.com");

	private static String _accessToken = "";
	
	public static AccessTokenResponseContent getAccessToken(String clientId, String clientSecret, String code) {
		AccessTokenResponseContent authResponse;

		String authResponseString;
		try {
			authResponseString = SLACK.path("/api/oauth.access")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret)
					.queryParam("code", code)
					.request().accept("application/json")
					.get(String.class);
		} catch (Exception e) {
			authResponseString = "Request Error:" + e.getMessage();
			authResponse = new AccessTokenResponseContent();
			authResponse.ok = "false";
			authResponse.error = authResponseString;
			return authResponse;
		}
		
		try {
			authResponse = JsonObject.from(authResponseString);
		} catch (RuntimeException e) {
			authResponseString = "Parse Error:" + e.getMessage() + ", Source:" + authResponseString;
			authResponse = new AccessTokenResponseContent();
			authResponse.ok = "false";
			authResponse.error = authResponseString;
			return authResponse;
		}
		
		if (!"true".equalsIgnoreCase(authResponse.ok)) {
			System.out.println(String.format(
					"Get AccessToken was failed!! cause='%s'", authResponseString));
		}

		authResponse.source = authResponseString;
		return authResponse;
	}
	
	public static void SetAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	public static PostMessageResponseContent postMessage(String postChannel, String text) {
		PostMessageResponseContent postResponse;
		
		String postResponseString;
		try {
			postResponseString = SLACK.path("/api/chat.postMessage")
					.queryParam("token", _accessToken)
					.queryParam("channel", postChannel)
					.queryParam("text", text)
					.request().accept("application/json")
					.get(String.class);
		} catch (Exception e) {
			postResponseString = "Parse Error:" + e.getMessage();
			postResponse = new PostMessageResponseContent();
			postResponse.ok = "false";
			postResponse.error = "Request Error:" + e.getMessage();
			return postResponse;
		}

		try {
			postResponse = JsonObject.from(postResponseString);
		} catch (Exception e) {
			postResponseString = "Parse Error:" + e.getMessage() + ", Source:" + postResponseString;
			postResponse = new PostMessageResponseContent();
			postResponse.ok = "false";
			postResponse.error = postResponseString;
			return postResponse;
		}
		
		if (!"true".equalsIgnoreCase(postResponse.ok)) {
			System.out.println(String.format(
					"Post Message was failed!! cause='%s'", postResponseString));
		}
		
		return postResponse;
	}

	public static UploadFileResponseContent uploadFile(String postChannel, InputStream fileContent, String fileName, String fileType, String title, String comment) {
		UploadFileResponseContent postResponse;

        var multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FormDataBodyPart("token", _accessToken));
        multiPart.bodyPart(new FormDataBodyPart("channels", postChannel));
        multiPart.bodyPart(new FormDataBodyPart("filetype", fileType));
        multiPart.bodyPart(new FormDataBodyPart("title", title));
        multiPart.bodyPart(new FormDataBodyPart("initial_comment", comment));
        multiPart.bodyPart(new StreamDataBodyPart("file", fileContent, fileName));

        String postResponseString;
		try {
			postResponseString = SLACK_UPLOAD.path("/api/files.upload")
					.request().accept("application/json")
					.post(Entity.entity(multiPart, multiPart.getMediaType()))
					.readEntity(String.class);
		} catch (Exception e) {
			postResponseString = "Parse Error:" + e.getMessage();
			postResponse = new UploadFileResponseContent();
			postResponse.ok = "false";
			postResponse.error = "Request Error:" + e.getMessage();
			return postResponse;
		}

		try {
			postResponse = JsonObject.from(postResponseString);
		} catch (Exception e) {
			postResponseString = "Parse Error:" + e.getMessage() + ", Source:" + postResponseString;
			postResponse = new UploadFileResponseContent();
			postResponse.ok = "false";
			postResponse.error = postResponseString;
			return postResponse;
		}
		
		if (!"true".equalsIgnoreCase(postResponse.ok)) {
			System.out.println(String.format(
					"Upload File was failed!! cause='%s'", postResponseString));
		}
		
		return postResponse;
	}
}
