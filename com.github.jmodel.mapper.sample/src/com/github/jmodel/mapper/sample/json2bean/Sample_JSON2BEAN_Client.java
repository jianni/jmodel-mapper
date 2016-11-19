package com.github.jmodel.mapper.sample.json2bean;

import java.io.InputStream;

import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.EngineFactoryService;
import com.github.jmodel.mapper.api.IllegalException;

public class Sample_JSON2BEAN_Client {

	public static void main(String[] args) {

		// prepare an JSON input stream
		InputStream f = new Sample_JSON2BEAN_Client().getClass().getResourceAsStream("Sample_JSON2BEAN_Data.json");

		// json2bean usage
		
		Engine<Object> convertEngine = EngineFactoryService.getInstance().getEngine(Object.class);
		try {
			Object output = convertEngine.convert(f, "com.github.jmodel.mapper.sample.json2bean.Sample_JSON2BEAN");
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
