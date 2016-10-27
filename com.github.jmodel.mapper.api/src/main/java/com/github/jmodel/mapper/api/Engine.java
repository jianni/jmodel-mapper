package com.github.jmodel.mapper.api;

import java.util.Locale;
import java.util.Map;

public interface Engine<R> {

	public <T> R convert(T sourceObj, String mappingURI) throws IllegalException;

	public <T> R convert(T sourceObj, String mappingURI, Map<String, Object> argsMap) throws IllegalException;

	public <T> R convert(T sourceObj, String mappingURI, Locale currentLocale) throws IllegalException;

	public <T> R convert(T sourceObj, String mappingURI, Map<String, Object> argsMap, Locale currentLocale)
			throws IllegalException;

}
