package com.github.jmodel.mapper.api;

public interface EngineFactory {

	public <T> Engine<T> createEngine(Class<T> returnType);
}
