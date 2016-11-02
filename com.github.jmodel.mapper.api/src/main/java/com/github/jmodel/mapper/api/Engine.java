package com.github.jmodel.mapper.api;

import java.util.Locale;
import java.util.Map;

public interface Engine<R> {

	public <T> R convert(T sourceObj, String mappingURI);

	public <T> R convert(T sourceObj, String mappingURI, Map<String, Object> argsMap);

	public <T> R convert(T sourceObj, String mappingURI, Locale currentLocale);

	public <T> R convert(T sourceObj, String mappingURI, Map<String, Object> argsMap, Locale currentLocale);

	public <T> R autoConvert(T sourceObj, FormatEnum fromFormat, FormatEnum toFormat);

	public <T> R autoConvert(T sourceObj, FormatEnum fromFormat, FormatEnum toFormat, Locale currentLocale);

}
