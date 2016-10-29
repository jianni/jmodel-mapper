package com.github.jmodel.mapper.impl.engines;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactoryService;
import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.impl.AbstractConvertEngine;

public class ConvertToJsonNodeEngine extends AbstractConvertEngine implements Engine<JsonNode> {

	public <T> JsonNode convert(T sourceObj, String mappingURI) {
		return convert(sourceObj, mappingURI, null, Locale.getDefault());
	}

	public <T> JsonNode convert(T sourceObj, String mappingURI, Locale currentLocale) {
		return convert(sourceObj, mappingURI, null, currentLocale);
	}

	@Override
	public <T> JsonNode convert(T sourceObj, String mappingURI, Map<String, Object> argsMap) {
		return convert(sourceObj, mappingURI, argsMap, Locale.getDefault());
	}

	@Override
	public <T> JsonNode convert(T sourceObj, String mappingURI, Map<String, Object> argsMap, Locale currentLocale) {
		return (JsonNode) super.getResult(sourceObj, mappingURI, argsMap, currentLocale);
	}

	@Override
	protected Builder<JsonNode> getBuilder(FormatEnum toFormat) {
		return BuilderFactoryService.getInstance().getBuilder(toFormat, JsonNode.class);
	}
}
