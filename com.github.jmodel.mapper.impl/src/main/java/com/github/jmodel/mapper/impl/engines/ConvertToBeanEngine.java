package com.github.jmodel.mapper.impl.engines;

import java.util.Locale;
import java.util.Map;

import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactoryService;
import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.impl.AbstractConvertEngine;

public class ConvertToBeanEngine extends AbstractConvertEngine implements Engine<Object> {

	public <T> Object convert(T sourceObj, String mappingURI) {
		return convert(sourceObj, mappingURI, null, Locale.getDefault());
	}

	public <T> Object convert(T sourceObj, String mappingURI, Locale currentLocale) {
		return convert(sourceObj, mappingURI, null, currentLocale);
	}

	@Override
	public <T> Object convert(T sourceObj, String mappingURI, Map<String, Object> argsMap) {
		return convert(sourceObj, mappingURI, argsMap, Locale.getDefault());
	}

	@Override
	public <T> Object convert(T sourceObj, String mappingURI, Map<String, Object> argsMap, Locale currentLocale) {
		return (Object) super.getResult(sourceObj, mappingURI, argsMap, currentLocale);
	}

	@Override
	protected Builder<Object> getBuilder(FormatEnum toFormat) {
		return BuilderFactoryService.getInstance().getBuilder(toFormat, Object.class);
	}

	@Override
	public <T> Object autoConvert(T sourceObj, FormatEnum fromFormat, FormatEnum toFormat) {
		return autoConvert(sourceObj, fromFormat, toFormat, Locale.getDefault());
	}

	@Override
	public <T> Object autoConvert(T sourceObj, FormatEnum fromFormat, FormatEnum toFormat, Locale currentLocale) {
		return (Object) super.getResult(sourceObj, fromFormat, toFormat, currentLocale);
	}
}
