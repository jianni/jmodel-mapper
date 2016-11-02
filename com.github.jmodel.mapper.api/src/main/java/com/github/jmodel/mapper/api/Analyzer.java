package com.github.jmodel.mapper.api;

public interface Analyzer {

	public <T> Model process(ModeEnum mode, Model sourceModel, T sourceObject);
}
