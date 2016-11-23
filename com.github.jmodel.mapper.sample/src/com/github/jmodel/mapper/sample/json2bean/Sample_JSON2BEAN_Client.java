package com.github.jmodel.mapper.sample.json2bean;

import java.io.InputStream;

import com.github.jmodel.api.IllegalException;
import com.github.jmodel.mapper.api.MappingEngine;
import com.github.jmodel.mapper.api.MappingEngineFactoryService;

public class Sample_JSON2BEAN_Client {

	public static void main(String[] args) {

		// prepare an JSON input stream
		InputStream f = new Sample_JSON2BEAN_Client().getClass().getResourceAsStream("Sample_JSON2BEAN_Data.json");

		// json2bean usage

		MappingEngine<Object> convertEngine = MappingEngineFactoryService.getInstance().getEngine(Object.class);
		try {
			Object output = convertEngine.convert(f, "com.github.jmodel.mapper.sample.json2bean.Sample_JSON2BEAN");
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
