package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.spi.EngineFactory;
import com.github.jmodel.mapper.impl.engines.ConvertToBeanEngine;
import com.github.jmodel.mapper.impl.engines.ConvertToJsonNodeEngine;
import com.github.jmodel.mapper.impl.engines.ConvertToStringEngine;

public class EngineFactoryImpl implements EngineFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <R> Engine<R> getEngine(Class<R> returnType) {
		if (returnType.getName().equals("java.lang.String")) {
			return (Engine<R>) new ConvertToStringEngine();
		} else if (returnType.getName().equals("com.fasterxml.jackson.databind.JsonNode")) {
			return (Engine<R>) new ConvertToJsonNodeEngine();
		} else if (returnType.getName().equals("java.lang.Object")) {
			return (Engine<R>) new ConvertToBeanEngine();
		} else {
			return null;
		}
	}
}
