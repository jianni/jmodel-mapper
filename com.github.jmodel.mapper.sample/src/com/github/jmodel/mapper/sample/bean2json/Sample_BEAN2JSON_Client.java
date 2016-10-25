package com.github.jmodel.mapper.sample.bean2json;

import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.impl.EngineFactoryImpl;
import com.github.jmodel.mapper.impl.EntityImpl;
import com.github.jmodel.mapper.impl.FieldImpl;

public class Sample_BEAN2JSON_Client {

	public static void main(String[] args) {

		// prepare an object instance
		Entity entity = new EntityImpl();
		entity.setName("hello entity name");
		Field field = new FieldImpl();
		entity.getFields().add(field);
		field.setName("hello field name");

		// bean2json usage
		Engine<String> convertEngine = EngineFactoryImpl.instance.createEngine(String.class);
		try {
			String output = convertEngine.convert(entity, "com.github.jmodel.mapper.sample.bean2json.Sample_BEAN2JSON");
			System.out.println(output);
		} catch (IllegalException e) {
			e.printStackTrace();
		}
	}

}
