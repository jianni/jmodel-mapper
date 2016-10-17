package com.github.jmodel.mapper.impl.formats.json;

import java.io.StringWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.Model;

public class JSONBuilder implements Builder {

	private final JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
	private JsonFactory jsonFactory = new JsonFactory();

	public Object process(Model targetModel) {
		ObjectNode rootNode = jsonNodeFactory.objectNode();
		createJsonNode(jsonNodeFactory, rootNode, targetModel);
		StringWriter stringWriter = new StringWriter();

		try {
			JsonGenerator generator = jsonFactory.createGenerator(stringWriter);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeTree(generator, rootNode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stringWriter.toString();
	}

	private JsonNode createJsonNode(final JsonNodeFactory factory, ObjectNode node, Model model) {

		if (model != null) {
			List<Field> fields = ((Entity) model).getFields();
			if (fields != null) {
				for (Field field : fields) {
					node.put(field.getName(), field.getValue());
				}
			}

			List<Model> subModels = model.getSubModels();
			if (subModels != null) {
				for (Model subModel : subModels) {
					if (subModel instanceof Entity) {
						ObjectNode subNode = factory.objectNode();
						node.set(subModel.getName(), createJsonNode(factory, subNode, subModel));
					} else if (subModel instanceof Array) {
						List<Model> sub = subModel.getSubModels();
						ArrayNode sNode1 = factory.arrayNode();
						for (Model s : sub) {
							ObjectNode sNode2 = factory.objectNode();
							sNode1.add(createJsonNode(factory, sNode2, s));
						}
						node.set(subModel.getName(), sNode1);
					}
				}
			}
		}
		return node;
	}
}
