package jp.example.slackapp.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import jp.example.businesscard.BusinessCardGeneratorClient;

@Path("status")
public class StatusController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getStatus() {
		return Map.of("status", true);
	}

	protected static final Pattern REQUEST_GENERATE_BUSINESS_CARD_MESSAGE = Pattern.compile("^.*名刺.*(役割|肩書|肩書き|ロール)[は:=＝：]?(?<role>[^、,。.]+?)[、,。.]?(名前|なまえ|氏名)[は:=＝：](?<name>[^、,。.]+?)?[、,。.]?(会社)[は:=＝：](?<company>[^、,。.]+?)[、,。.]?$");

	@Path("/pdf")
	@GET
	@Produces("application/pdf")
	public StreamingOutput getPdf(@Context UriInfo uriInfo, @QueryParam("text") String text) {

		var matcher = REQUEST_GENERATE_BUSINESS_CARD_MESSAGE.matcher(text);
		var role = "初代会長";
		var name = "月極正太郎";
		var company = "月極駐車場株式会社";
		if (matcher.matches()) {
			role = matcher.group("role");
			name = matcher.group("name");
			company = matcher.group("company");
		}
		
		var pdfData = BusinessCardGeneratorClient.generate(
				"templates/business_card.mustache.html", 
				Map.of("role", role, "name", name, "company", company));

        return new StreamingOutput() {
            @Override
            public void write(OutputStream out)
                    throws IOException, WebApplicationException {
            	out.write(pdfData);
                out.flush();
            }
        };
	}
}
