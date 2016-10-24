package com.github.jmodel.mapper.impl.formats.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.Model;

public class JsonNodeBuilder implements Builder<JsonNode> {

	public JsonNode process(Model targetModel) {
		return JsonBuilderHelper.buildJsonNode(targetModel);
	}

}
