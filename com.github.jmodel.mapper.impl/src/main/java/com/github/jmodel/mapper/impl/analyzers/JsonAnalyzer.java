package com.github.jmodel.mapper.impl.analyzers;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.api.Model;
import com.github.jmodel.mapper.impl.AbstractAnalyzer;

public class JsonAnalyzer extends AbstractAnalyzer<JsonNode> {

	public <T> Model process(Model sourceModel, T sourceObject)  {
		JsonNode jsonNode;
		if (sourceObject instanceof JsonNode) {
			jsonNode = (JsonNode) sourceObject;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				if (sourceObject instanceof String) {
					jsonNode = mapper.readTree((String) sourceObject);
				} else if (sourceObject instanceof File) {
					jsonNode = mapper.readTree((File) sourceObject);
				} else if (sourceObject instanceof InputStream) {
					jsonNode = mapper.readTree((InputStream) sourceObject);
				} else {
					throw new IllegalException("source object is illegal");
				}
			} catch (Exception e) {
				throw new IllegalException("source object is illegal");
			}
		}
		populateModel(sourceModel, new HashMap<String, Field>(), new HashMap<String, Model>(), jsonNode);
		return sourceModel;
	}

	@Override
	protected void setFieldValue(JsonNode jsonNode, Field field) {
		JsonNode node = jsonNode.path(field.getName());
		field.setValue(node.asText());
	}

	@Override
	protected JsonNode getSubNode(JsonNode node, String subNodeName) {
		return node.path(subNodeName);
	}

	@Override
	protected void populateSubModel(JsonNode subNode, Model subModel, Model subSubModel) {
		for (int i = 0; i < subNode.size(); i++) {
			JsonNode subSubJsonNode = subNode.get(i);

			Model clonedSubSubModel = null;
			if (i == 0) {
				clonedSubSubModel = subSubModel;
			} else {
				clonedSubSubModel = subSubModel.clone();
				subModel.getSubModels().add(clonedSubSubModel);
			}
			clonedSubSubModel.setModelPath(subModel.getModelPath() + "." + clonedSubSubModel.getName() + "[" + i + "]");
			clonedSubSubModel.setFieldPathMap(subModel.getFieldPathMap());

			populateModel(clonedSubSubModel, subModel.getFieldPathMap(), subModel.getModelPathMap(), subSubJsonNode);
		}
	}
}
