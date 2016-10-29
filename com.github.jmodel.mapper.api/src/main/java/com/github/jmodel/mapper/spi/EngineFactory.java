package com.github.jmodel.mapper.spi;

import com.github.jmodel.mapper.api.Engine;

public interface EngineFactory {

	public <R> Engine<R> getEngine(Class<R> returnType);
}
