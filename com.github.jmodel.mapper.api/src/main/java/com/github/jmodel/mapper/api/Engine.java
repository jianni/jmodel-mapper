package com.github.jmodel.mapper.api;

public interface Engine {

	public <T extends Object, R extends Object> R convert(T sourceObj, String mappingURI) throws IllegalException;
	
}
