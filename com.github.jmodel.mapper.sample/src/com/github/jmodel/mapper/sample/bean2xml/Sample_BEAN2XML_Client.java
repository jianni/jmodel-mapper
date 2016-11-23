package com.github.jmodel.mapper.sample.bean2xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jmodel.api.Entity;
import com.github.jmodel.api.Field;
import com.github.jmodel.api.IllegalException;
import com.github.jmodel.impl.EntityImpl;
import com.github.jmodel.impl.FieldImpl;
import com.github.jmodel.mapper.api.MappingEngine;
import com.github.jmodel.mapper.api.MappingEngineFactoryService;

public class Sample_BEAN2XML_Client {

	public static void main(String[] args) {

		// prepare an object instance
		Entity entity = new EntityImpl();
		entity.setName("hello entity name");
		Field field = new FieldImpl();
		entity.getFields().add(field);
		field.setName("hello field name");

		// bean2json usage
		MappingEngine<String> convertEngine = MappingEngineFactoryService.getInstance().getEngine(String.class);
		Map<String, Object> argsMap = new HashMap<String, Object>();
		List<String> varNames = new ArrayList<String>();
		varNames.add("hello field name");

		argsMap.put("varName", varNames);
		try {
			String output = convertEngine.convert(entity,
					"com.github.jmodel.mapper.sample.bean2xml.Sample_BEAN2XML_Child", argsMap);
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
