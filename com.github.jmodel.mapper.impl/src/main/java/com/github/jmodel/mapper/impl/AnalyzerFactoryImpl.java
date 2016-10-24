package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.AnalyzerFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.formats.json.JsonAnalyzer;
import com.github.jmodel.mapper.impl.formats.xml.XmlAnalyzer;

public class AnalyzerFactoryImpl implements AnalyzerFactory {

	public Analyzer createAnalyzer(FormatEnum fromFormat) throws IllegalException {
		switch (fromFormat) {
		case JSON:
			return new JsonAnalyzer();
		case XML:
			return new XmlAnalyzer();
		default:
			throw new IllegalException("Customized AnalyzerFactory is expected");
		}
	}

}
