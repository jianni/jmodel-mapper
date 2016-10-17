package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.formats.json.JSONBuilder;
import com.github.jmodel.mapper.impl.formats.xml.XMLBuilder;

public class BuilderFactoryImpl implements BuilderFactory {

	public Builder createBuilder(FormatEnum toFormat) throws IllegalException {
		switch (toFormat) {
		case JSON:
			return new JSONBuilder();
		case XML:
			return new XMLBuilder();
		default:
			throw new IllegalException("Customized BuilderFactory is expected");
		}
	}

}
