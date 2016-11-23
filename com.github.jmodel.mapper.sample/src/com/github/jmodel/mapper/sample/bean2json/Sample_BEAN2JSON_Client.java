package com.github.jmodel.mapper.sample.bean2json;

import com.github.jmodel.api.Entity;
import com.github.jmodel.api.Field;
import com.github.jmodel.api.IllegalException;
import com.github.jmodel.impl.EntityImpl;
import com.github.jmodel.impl.FieldImpl;
import com.github.jmodel.mapper.api.MappingEngine;
import com.github.jmodel.mapper.api.MappingEngineFactoryService;

public class Sample_BEAN2JSON_Client {

	public static void main(String[] args) {

		// prepare an object instance
		Entity entity = new EntityImpl();
		entity.setName("hello entity name");
		Field field = new FieldImpl();
		entity.getFields().add(field);
		field.setName("hello field name");

		// bean2json usage
		MappingEngine<String> convertEngine = MappingEngineFactoryService.getInstance().getEngine(String.class);
		try {
			String output = convertEngine.convert(entity, "com.github.jmodel.mapper.sample.bean2json.Sample_BEAN2JSON");
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
