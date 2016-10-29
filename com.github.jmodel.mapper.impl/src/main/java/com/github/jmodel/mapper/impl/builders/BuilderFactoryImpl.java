package com.github.jmodel.mapper.impl.builders;

import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.spi.BuilderFactory;

public class BuilderFactoryImpl implements BuilderFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <R> Builder<R> getBuilder(FormatEnum toFormat, Class<R> returnType) {
		switch (toFormat) {
		case JSON:
			if (returnType.getName().equals("com.fasterxml.jackson.databind.JsonNode")) {
				return (Builder<R>) (new JsonNodeBuilder());
			} else if (returnType.getName().equals("java.lang.String")) {
				return (Builder<R>) (new JsonStringBuilder());
			}
		case XML:
			if (returnType.getName().equals("java.lang.String")) {
				return (Builder<R>) (new XmlStringBuilder());
			}
		default:
			return null;
		}

	}
}
