package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.AnalyzerFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.formats.json.JSONAnalyzer;
import com.github.jmodel.mapper.impl.formats.xml.XMLAnalyzer;

public class AnalyzerFactoryImpl implements AnalyzerFactory {

	public Analyzer createAnalyzer(FormatEnum fromFormat) throws IllegalException {
		switch (fromFormat) {
		case JSON:
			return new JSONAnalyzer();
		case XML:
			return new XMLAnalyzer();
		default:
			throw new IllegalException("Customized AnalyzerFactory is expected");
		}
	}

}
