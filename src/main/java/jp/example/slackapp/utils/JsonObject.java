package jp.example.slackapp.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObject {
	
	private static final ObjectMapper _objectMapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public static <R> R from(String jsonContent, R ... javaType) {
		try {
			return (R) _objectMapper.readValue(jsonContent, javaType.getClass().getComponentType());
		} catch (JsonParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
