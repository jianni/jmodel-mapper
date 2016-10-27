package com.github.jmodel.mapper.impl.builders;

import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;

public class BuilderFactoryStringImpl implements BuilderFactory<String> {

	public Builder<String> createBuilder(FormatEnum toFormat) {
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
