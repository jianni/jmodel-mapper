package com.github.jmodel.mapper.impl.builders;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;

public class BuilderFactoryJsonNodeImpl implements BuilderFactory<JsonNode> {

	public Builder<JsonNode> createBuilder(FormatEnum toFormat) {
		switch (toFormat) {
		case JSON:
			return new JsonNodeBuilder();
		default:
			throw new IllegalException("Customized builder factory is expected");
		}
	}

}
