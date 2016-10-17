package com.github.jmodel.mapper.api;

public interface BuilderFactory {

	public Builder createBuilder(FormatEnum toFormat) throws IllegalException;
}
