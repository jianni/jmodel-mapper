package com.github.jmodel.mapper.impl.formats.xml;

import com.github.jmodel.mapper.api.FormatChecker;

public class XMLFormatChecker implements FormatChecker {

	//TODO
	public boolean isValid(Object sourceObject) {
		if (sourceObject instanceof String) {
			return true;
		}
		return false;
	}

}
