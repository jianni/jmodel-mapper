package com.github.jmodel.mapper.sample.xml2xml;

import java.io.InputStream;

import com.github.jmodel.api.IllegalException;
import com.github.jmodel.mapper.api.MappingEngine;
import com.github.jmodel.mapper.api.MappingEngineFactoryService;


public class Sample_XML2XML_Client {

	public static void main(String[] args) {

		// prepare an XML input stream
		InputStream f = new Sample_XML2XML_Client().getClass().getResourceAsStream("Sample_XML2XML_Data.xml");

		// xml2xml usage
		MappingEngine<String> convertEngine = MappingEngineFactoryService.getInstance().getEngine(String.class);
		try {
			String output = convertEngine.convert(f, "com.github.jmodel.mapper.sample.xml2xml.Sample_XML2XML");
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}

	}

}
