package com.github.jmodel.mapper.spi;

import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.FormatEnum;

public interface BuilderFactory {

	public <R> Builder<R> getBuilder(FormatEnum toFormat, Class<R> returnType);
}
