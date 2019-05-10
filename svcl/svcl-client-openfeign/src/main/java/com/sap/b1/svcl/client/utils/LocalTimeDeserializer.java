package com.sap.b1.svcl.client.utils;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

	@Override
	public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException 
	{
		String str = p.readValueAs(String.class);
		return LocalTime.parse(str);
	}

}
