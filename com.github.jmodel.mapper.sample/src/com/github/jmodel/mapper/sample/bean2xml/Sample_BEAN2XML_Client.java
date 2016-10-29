package com.github.jmodel.mapper.sample.bean2xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.EngineFactoryService;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.EntityImpl;
import com.github.jmodel.mapper.impl.FieldImpl;

public class Sample_BEAN2XML_Client {

	public static void main(String[] args) {

		// prepare an object instance
		Entity entity = new EntityImpl();
		entity.setName("hello entity name");
		Field field = new FieldImpl();
		entity.getFields().add(field);
		field.setName("hello field name");

		// bean2json usage
		Engine<String> convertEngine = EngineFactoryService.getInstance().getEngine(String.class);
		Map<String, Object> argsMap = new HashMap<String, Object>();
		List<String> varNames = new ArrayList<String>();
		varNames.add("hello field name1");
		
		argsMap.put("varName", varNames);
		try {
			String output = convertEngine.convert(entity, "com.github.jmodel.mapper.sample.bean2xml.Sample_BEAN2XML_Child",
					argsMap);
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
