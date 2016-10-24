package com.github.jmodel.mapper.impl.formats.json;

import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.Model;

public class JsonStringBuilder implements Builder<String> {
	
	private JsonFactory jsonFactory = new JsonFactory();

	public String process(Model targetModel) {
		JsonNode rootNode = JsonBuilderHelper.buildJsonNode(targetModel);
		StringWriter stringWriter = new StringWriter();

		try {
			JsonGenerator generator = jsonFactory.createGenerator(stringWriter);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeTree(generator, rootNode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stringWriter.toString();
	}

	
}
