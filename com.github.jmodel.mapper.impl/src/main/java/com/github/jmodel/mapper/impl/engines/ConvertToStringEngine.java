package com.github.jmodel.mapper.impl.engines;

import java.util.Locale;
import java.util.Map;

import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.AbstractConvertEngine;
import com.github.jmodel.mapper.impl.builders.BuilderFactoryStringImpl;

public class ConvertToStringEngine extends AbstractConvertEngine implements Engine<String> {

	public <T> String convert(T sourceObj, String mappingURI) throws IllegalException {
		return convert(sourceObj, mappingURI, null, Locale.getDefault());
	}

	public <T> String convert(T sourceObj, String mappingURI, Locale currentLocale) throws IllegalException {
		return convert(sourceObj, mappingURI, null, currentLocale);
	}

	@Override
	public <T> String convert(T sourceObj, String mappingURI, Map<String, Object> argsMap) throws IllegalException {
		return convert(sourceObj, mappingURI, argsMap, Locale.getDefault());
	}

	@Override
	public <T> String convert(T sourceObj, String mappingURI, Map<String, Object> argsMap, Locale currentLocale)
			throws IllegalException {
		return (String) super.getResult(sourceObj, mappingURI, argsMap, currentLocale);
	}

	@Override
	protected BuilderFactory<String> getBuilderFactory() {
		return new BuilderFactoryStringImpl();
	}

}
