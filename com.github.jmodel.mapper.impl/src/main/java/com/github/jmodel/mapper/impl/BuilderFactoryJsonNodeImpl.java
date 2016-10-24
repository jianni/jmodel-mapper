package com.github.jmodel.mapper.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.formats.json.JsonNodeBuilder;

public class BuilderFactoryJsonNodeImpl implements BuilderFactory<JsonNode> {

	public Builder<JsonNode> createBuilder(FormatEnum toFormat) throws IllegalException {
		switch (toFormat) {
		case JSON:
			return new JsonNodeBuilder();
		default:
			throw new IllegalException("Customized builder factory is expected");
		}
	}

}
