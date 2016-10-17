package com.github.jmodel.mapper.impl;

import com.github.jmodel.mapper.api.FormatChecker;
import com.github.jmodel.mapper.api.FormatCheckerFactory;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.formats.json.JSONFormatChecker;
import com.github.jmodel.mapper.impl.formats.xml.XMLFormatChecker;

public class FormatCheckerFactoryImpl implements FormatCheckerFactory {

	public FormatChecker createFormatChecker(FormatEnum format) throws IllegalException {
		switch (format) {
		case JSON:
			return new JSONFormatChecker();
		case XML:
			return new XMLFormatChecker();
		default:
			throw new IllegalException("Customized FormatCheckerFactory is expected");
		}
	}
}
