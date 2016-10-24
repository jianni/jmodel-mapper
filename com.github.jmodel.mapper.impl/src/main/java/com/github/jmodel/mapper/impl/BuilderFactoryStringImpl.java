package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.formats.json.JsonStringBuilder;
import com.github.jmodel.mapper.impl.formats.xml.XmlStringBuilder;

public class BuilderFactoryStringImpl implements BuilderFactory<String> {

	public Builder<String> createBuilder(FormatEnum toFormat) throws IllegalException {
		switch (toFormat) {
		case JSON:
			return new JsonStringBuilder();
		case XML:
			return new XmlStringBuilder();
		default:
			throw new IllegalException("Customized builder factory is expected");
		}
	}

}