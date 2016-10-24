package com.github.jmodel.mapper.impl;

import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.IllegalException;

public class ConvertToJsonNodeEngine extends AbstractConvertEngine implements Engine<JsonNode> {

	public <T> JsonNode convert(T sourceObj, String mappingURI) throws IllegalException {
		return convert(sourceObj, mappingURI, Locale.getDefault());
	}

	public <T> JsonNode convert(T sourceObj, String mappingURI, Locale currentLocale) throws IllegalException {
		return (JsonNode) super.getResult(sourceObj, mappingURI, currentLocale);
	}

	@Override
	protected BuilderFactory<JsonNode> getBuilderFactory() {
		return new BuilderFactoryJsonNodeImpl();
	}

}
