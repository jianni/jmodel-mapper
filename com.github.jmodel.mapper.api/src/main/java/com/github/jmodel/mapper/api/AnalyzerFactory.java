package com.github.jmodel.mapper.api;

public interface AnalyzerFactory {

	public Analyzer createAnalyzer(FormatEnum fromFormat) throws IllegalException;
}
