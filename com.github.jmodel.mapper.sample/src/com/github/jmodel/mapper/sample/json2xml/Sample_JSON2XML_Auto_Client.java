package com.github.jmodel.mapper.sample.json2xml;

import java.io.InputStream;

import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.EngineFactoryService;
import com.github.jmodel.mapper.api.FormatEnum;
import com.github.jmodel.mapper.api.IllegalException;

public class Sample_JSON2XML_Auto_Client {

	public static void main(String[] args) {

		// prepare an JSON input stream
		InputStream f = new Sample_JSON2XML_Auto_Client().getClass().getResourceAsStream("Sample_JSON2XML_Data.json");

		// json2xml usage

		Engine<String> convertEngine = EngineFactoryService.getInstance().getEngine(String.class);
		try {
			String output = convertEngine.autoConvert(f, FormatEnum.JSON, FormatEnum.XML);
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
