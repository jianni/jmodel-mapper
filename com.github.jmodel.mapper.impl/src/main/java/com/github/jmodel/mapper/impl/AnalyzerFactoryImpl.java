package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.AnalyzerFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.analyzers.BeanAnalyzer;
import com.github.jmodel.mapper.impl.analyzers.JsonAnalyzer;
import com.github.jmodel.mapper.impl.analyzers.XmlAnalyzer;

public class AnalyzerFactoryImpl implements AnalyzerFactory {

	public Analyzer createAnalyzer(FormatEnum fromFormat) {
		switch (fromFormat) {
		case JSON:
			return new JsonAnalyzer();
		case XML:
			return new XmlAnalyzer();
		case BEAN:
			return new BeanAnalyzer();
		default:
			throw new IllegalException("Customized AnalyzerFactory is expected");
		}
	}

}
