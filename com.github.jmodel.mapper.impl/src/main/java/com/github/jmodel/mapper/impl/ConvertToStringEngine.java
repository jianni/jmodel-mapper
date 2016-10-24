package com.github.jmodel.mapper.impl;

import java.util.Locale;

import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.IllegalException;

public class ConvertToStringEngine extends AbstractConvertEngine implements Engine<String> {

	public <T> String convert(T sourceObj, String mappingURI) throws IllegalException {
		return convert(sourceObj, mappingURI, Locale.getDefault());
	}

	public <T> String convert(T sourceObj, String mappingURI, Locale currentLocale) throws IllegalException {
		return (String) super.getResult(sourceObj, mappingURI, currentLocale);
	}

	@Override
	protected BuilderFactory<String> getBuilderFactory() {
		return new BuilderFactoryStringImpl();
	}

}
