package com.github.jmodel.mapper.api;

public interface Analyzer {

	public <T> Model process(Model sourceModel, T sourceObject);
}
