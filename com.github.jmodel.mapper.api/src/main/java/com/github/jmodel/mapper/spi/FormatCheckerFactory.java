package com.github.jmodel.mapper.spi;

import com.github.jmodel.mapper.api.FormatChecker;
import com.github.jmodel.mapper.api.FormatEnum;

public interface FormatCheckerFactory {

	public FormatChecker getFormatChecker(FormatEnum fromFormat);
}
