package com.github.jmodel.mapper.api;

public interface BuilderFactory<R> {

	public Builder<R> createBuilder(FormatEnum toFormat) throws IllegalException;
}
