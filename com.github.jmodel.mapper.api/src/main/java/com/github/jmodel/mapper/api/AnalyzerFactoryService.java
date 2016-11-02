package com.github.jmodel.mapper.api;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import com.github.jmodel.mapper.spi.AnalyzerFactory;

public class AnalyzerFactoryService {

	private static AnalyzerFactoryService service;
	private ServiceLoader<AnalyzerFactory> loader;

	private AnalyzerFactoryService() {
		loader = ServiceLoader.load(AnalyzerFactory.class);
	}

	public static synchronized AnalyzerFactoryService getInstance() {
		if (service == null) {
			service = new AnalyzerFactoryService();
		}
		return service;
	}

	public Analyzer getAnalyzer(ModeEnum mode, FormatEnum format, String extendAnalyzerName) {
		Analyzer analyzer = null;

		try {
			Iterator<AnalyzerFactory> analyzerFactorys = loader.iterator();
			while (analyzer == null && analyzerFactorys.hasNext()) {
				AnalyzerFactory analyzerFactory = analyzerFactorys.next();
				analyzer = analyzerFactory.getAnalyzer(mode, format, extendAnalyzerName);
			}
		} catch (ServiceConfigurationError serviceError) {
			analyzer = null;
			serviceError.printStackTrace();

		}
		return analyzer;
	}
}
