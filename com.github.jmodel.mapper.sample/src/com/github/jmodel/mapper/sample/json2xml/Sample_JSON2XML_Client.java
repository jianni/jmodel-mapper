package com.github.jmodel.mapper.sample.json2xml;

import java.io.InputStream;

import com.github.jmodel.api.IllegalException;
import com.github.jmodel.mapper.api.MappingEngine;
import com.github.jmodel.mapper.api.MappingEngineFactoryService;

public class Sample_JSON2XML_Client {

	public static void main(String[] args) {

		// prepare an JSON input stream
		InputStream f = new Sample_JSON2XML_Client().getClass().getResourceAsStream("Sample_JSON2XML_Data.json");

		// json2xml usage

		MappingEngine<String> convertEngine = MappingEngineFactoryService.getInstance().getEngine(String.class);
		try {
			String output = convertEngine.convert(f, "com.github.jmodel.mapper.sample.json2xml.Sample_JSON2XML");
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
