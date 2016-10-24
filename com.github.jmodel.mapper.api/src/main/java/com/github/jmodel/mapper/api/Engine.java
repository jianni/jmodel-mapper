package com.github.jmodel.mapper.api;

import java.util.Locale;

public interface Engine<R> {

	public <T> R convert(T sourceObj, String mappingURI) throws IllegalException;

	public <T> R convert(T sourceObj, String mappingURI, Locale currentLocale) throws IllegalException;

}
