package com.github.jmodel.mapper.api;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import com.github.jmodel.mapper.spi.EngineFactory;

public class EngineFactoryService {

	private static EngineFactoryService service;
	private ServiceLoader<EngineFactory> loader;

	private EngineFactoryService() {
		loader = ServiceLoader.load(EngineFactory.class);
	}

	public static synchronized EngineFactoryService getInstance() {
		if (service == null) {
			service = new EngineFactoryService();
		}
		return service;
	}

	public <R> Engine<R> getEngine(Class<R> returnType) {
		Engine<R> engine = null;

		try {
			Iterator<EngineFactory> engineFactorys = loader.iterator();
			while (engine == null && engineFactorys.hasNext()) {
				EngineFactory engineFactory = engineFactorys.next();
				engine = engineFactory.getEngine(returnType);
			}
		} catch (ServiceConfigurationError serviceError) {
			engine = null;
			serviceError.printStackTrace();

		}
		return engine;
	}
}
