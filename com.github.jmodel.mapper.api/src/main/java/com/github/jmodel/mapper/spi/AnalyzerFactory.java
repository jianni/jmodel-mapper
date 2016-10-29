package com.github.jmodel.mapper.spi;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.FormatEnum;

public interface AnalyzerFactory {

	public Analyzer getAnalyzer(FormatEnum fromFormat, String extendAnalyzerName);
}
