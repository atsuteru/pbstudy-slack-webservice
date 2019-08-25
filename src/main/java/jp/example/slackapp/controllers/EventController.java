package jp.example.slackapp.controllers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import jp.example.slackapp.BusinessCardGenerator;
import jp.example.slackapp.SlackClient;
import jp.example.slackapp.controllers.contents.EventAppMentionContent;
import jp.example.slackapp.controllers.contents.EventCallbackRequestContent;
import jp.example.slackapp.controllers.contents.EventEnableRequestContent;
import jp.example.slackapp.controllers.contents.EventEnableResponseContent;
import jp.example.slackapp.utils.JsonObject;

@Path("event")
public class EventController {

	protected Map<String, Function<String, EventEnableResponseContent>> _actions = new HashMap<String, Function<String, EventEnableResponseContent>>();
	
	protected UriInfo _uriInfo;
	
	protected String getServletUrl() {
		return String.format("%s://%s", _uriInfo.getBaseUri().getScheme(), _uriInfo.getBaseUri().getAuthority());
	}
	
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
	public EventEnableResponseContent receive(String jsonContent, @Context UriInfo uriInfo) {
		_uriInfo = uriInfo;

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
	
	protected static final Pattern REQUEST_GENERATE_BUSINESS_CARD_MESSAGE = Pattern.compile("^.*名刺.*(役割|肩書|肩書き|ロール)[は:=＝：]?(?<role>[^、,。.]+?)[、,。.]?(名前|なまえ|氏名)[は:=＝：](?<name>[^、,。.]+?)?[、,。.]?(会社)[は:=＝：](?<campany>[^、,。.]+?)[、,。.]?$");

	protected EventEnableResponseContent receiveAppMention(EventAppMentionContent content) {
		System.out.println(String.format("Receive App menthion '%s' from user '%s' in channel'%s'", content.event.text, content.event.user, content.event.channel));

		var matcher = REQUEST_GENERATE_BUSINESS_CARD_MESSAGE.matcher(content.event.text);
		if (!matcher.matches()) {
			SlackClient.postMessageAsUser(
					content.event.channel, 
					String.format("<@%s> こんにちは!! 何か依頼がありますか？ 名刺を作成する場合は、「名刺、役割=スタッフ、氏名=月極正太郎、会社=月極駐車場株式会社」のようにご依頼ください。", content.event.user));
			return new EventEnableResponseContent();
		}

		var role = matcher.group("role");
		var name = matcher.group("name");
		var campany = matcher.group("campany");
		
		SlackClient.postMessageAsUser(
				content.event.channel, 
				String.format("<@%s> 次の内容で名刺の作成を承りました。役割='%s', 氏名='%s', 所属=%s. 完成したらこのチャンネルに投稿しておきます。", content.event.user, role, name, campany));
		
		var pdfData = BusinessCardGenerator.Generate(
				"templates/business_card.mustache.html", 
				Map.of("role", role, "name", name, "campany", campany), 
				getServletUrl());
		
		SlackClient.uploadFile(
				content.event.channel, 
				new ByteArrayInputStream(pdfData), "BusinessCard.pdf", "pdf",
				"BusinessCard.pdf", "名刺が出来あがりました！マルチカード台紙に印刷してご利用ください♪");

		return new EventEnableResponseContent();
	}
}
