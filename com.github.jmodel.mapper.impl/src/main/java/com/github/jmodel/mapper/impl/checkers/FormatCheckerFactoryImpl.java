package com.github.jmodel.mapper.impl.checkers;

import com.github.jmodel.mapper.api.FormatChecker;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.spi.FormatCheckerFactory;

public class FormatCheckerFactoryImpl implements FormatCheckerFactory {

	public FormatChecker getFormatChecker(FormatEnum format) {
		switch (format) {
		case JSON:
			return new JsonFormatChecker();
		case XML:
			return new XmlFormatChecker();
		case BEAN:
			return new BeanFormatChecker();
		default:
			return null;
		}
	}
}
