package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.FormatChecker;
import com.github.jmodel.mapper.api.FormatCheckerFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.formats.BeanFormatChecker;
import com.github.jmodel.mapper.impl.formats.JsonFormatChecker;
import com.github.jmodel.mapper.impl.formats.XmlFormatChecker;

public class FormatCheckerFactoryImpl implements FormatCheckerFactory {

	public FormatChecker createFormatChecker(FormatEnum format) {
		switch (format) {
		case JSON:
			return new JsonFormatChecker();
		case XML:
			return new XmlFormatChecker();
		case BEAN:
			return new BeanFormatChecker();
		default:
			throw new IllegalException("Customized FormatCheckerFactory is expected");
		}
	}
}
