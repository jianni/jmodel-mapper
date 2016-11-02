package com.github.jmodel.mapper.impl.analyzers;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.api.ModeEnum;
import com.github.jmodel.mapper.api.Model;
import com.github.jmodel.mapper.impl.AbstractAnalyzer;
import com.github.jmodel.mapper.impl.ArrayImpl;
import com.github.jmodel.mapper.impl.EntityImpl;
import com.github.jmodel.mapper.impl.FieldImpl;

public class JsonAnalyzer extends AbstractAnalyzer<JsonNode> {

	public <T> Model process(ModeEnum mode, Model sourceModel, T sourceObject) {
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
		populateInstance(mode, sourceModel, new HashMap<String, Field>(), new HashMap<String, Model>(), jsonNode);
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

			populateInstanceManually(clonedSubSubModel, subModel.getFieldPathMap(), subModel.getModelPathMap(),
					subSubJsonNode);
		}
	}

	@Override
	protected void populateInstanceAutomatically(final Model sourceModel, final Map<String, Field> fieldPathMap,
			final Map<String, Model> modelPathMap, final String nodeName, final JsonNode node) {
		if (node.isObject()) {
			final ObjectNode objectNode = (ObjectNode) node;

			if (nodeName.trim().length() == 0) {
				sourceModel.setModelPathMap(modelPathMap);
				sourceModel.setFieldPathMap(fieldPathMap);
				sourceModel.setName("root");
				sourceModel.setModelPath("root");
				sourceModel.getModelPathMap().put("root", sourceModel);

				objectNode.fields().forEachRemaining((subNode) -> {
					if (subNode.getValue().isObject() || subNode.getValue().isArray()) {
						populateInstanceAutomatically(sourceModel, sourceModel.getFieldPathMap(),
								sourceModel.getModelPathMap(), subNode.getKey(), subNode.getValue());
					} else {
						Field field = new FieldImpl();
						field.setName(subNode.getKey());
						field.setValue(subNode.getValue().asText());
						field.setParentEntity((Entity) sourceModel);
						((Entity) sourceModel).getFields().add(field);
						sourceModel.getFieldPathMap().put(sourceModel.getModelPath() + '.' + field.getName(), field);
					}
				});
			} else {
				final Entity entity = new EntityImpl();
				entity.setName(nodeName);
				if (sourceModel instanceof Array) {
					entity.setModelPath(sourceModel.getModelPath() + "." + entity.getName() + "["
							+ (sourceModel.getSubModels().size() - 1) + "]");
				} else {
					entity.setModelPath(sourceModel.getModelPath() + "." + entity.getName());
				}
				entity.setParentModel(sourceModel);
				entity.setFieldPathMap(sourceModel.getFieldPathMap());
				entity.setModelPathMap(sourceModel.getModelPathMap());
				sourceModel.getSubModels().add(entity);
				sourceModel.getModelPathMap().put(entity.getModelPath(), entity);

				// process child nodes
				objectNode.fields().forEachRemaining((subNode) -> {
					if (subNode.getValue().isObject() || subNode.getValue().isArray()) {
						populateInstanceAutomatically(entity, entity.getFieldPathMap(), entity.getModelPathMap(),
								subNode.getKey(), subNode.getValue());
					} else {
						Field field = new FieldImpl();
						field.setName(subNode.getKey());
						field.setValue(subNode.getValue().asText());
						field.setParentEntity(entity);

						entity.getFields().add(field);
						sourceModel.getFieldPathMap().put(entity.getModelPath() + '.' + field.getName(), field);
					}
				});
			}
		} else if (node.isArray()) {
			ArrayNode arrayNode = (ArrayNode) node;
			final Array array = new ArrayImpl();
			array.setName(nodeName);
			array.setModelPath(sourceModel.getModelPath() + "." + array.getName() + "[]");
			array.setParentModel(sourceModel);
			array.setFieldPathMap(sourceModel.getFieldPathMap());
			array.setModelPathMap(sourceModel.getModelPathMap());
			sourceModel.getSubModels().add(array);
			sourceModel.getModelPathMap().put(array.getModelPath(), array);

			arrayNode.forEach((subNode) -> {
				populateInstanceAutomatically(array, array.getFieldPathMap(), array.getModelPathMap(), nodeName,
						subNode);
			});
		}
	}
}
