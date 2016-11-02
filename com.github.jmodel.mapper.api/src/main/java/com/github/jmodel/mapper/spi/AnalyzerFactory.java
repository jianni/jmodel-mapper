package com.github.jmodel.mapper.spi;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.ModeEnum;

public interface AnalyzerFactory {

	public Analyzer getAnalyzer(ModeEnum mode, FormatEnum fromFormat, String extendAnalyzerName);
}
