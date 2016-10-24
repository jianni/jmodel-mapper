package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.EngineFactory;
import com.github.jmodel.mapper.impl.engines.ConvertToJsonNodeEngine;
import com.github.jmodel.mapper.impl.engines.ConvertToStringEngine;

public class EngineFactoryImpl implements EngineFactory {

	public static final EngineFactoryImpl instance = new EngineFactoryImpl();

	private EngineFactoryImpl() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Engine<T> createEngine(Class<T> returnType) {
		if (returnType.getName().equals("java.lang.String")) {
			return (Engine<T>) new ConvertToStringEngine();
		}
		if (returnType.getName().equals("com.fasterxml.jackson.databind.JsonNode")) {
			return (Engine<T>) new ConvertToJsonNodeEngine();
		}
		return null;
	}
}
