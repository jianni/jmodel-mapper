package com.github.jmodel.mapper.impl.formats.json;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.api.Model;

public class JSONAnalyzer implements Analyzer {

	public <T> Model process(Model sourceModel, T sourceObject) throws IllegalException {
		// filtered by format checker previously
		JsonNode jsonNode;
		if (sourceObject instanceof JsonNode) {
			jsonNode = (JsonNode) sourceObject;
		} else {
			ObjectMapper mapper = new ObjectMapper();
			if (sourceObject instanceof String) {
				try {
					jsonNode = mapper.readTree((String) sourceObject);
				} catch (Exception e) {
					throw new IllegalException("source object is illegal");
				}
			} else if (sourceObject instanceof File) {
				try {
					jsonNode = mapper.readTree((File) sourceObject);
				} catch (Exception e) {
					throw new IllegalException("source object is illegal");
				}
			} else if (sourceObject instanceof InputStream) {
				try {
					jsonNode = mapper.readTree((InputStream) sourceObject);
				} catch (Exception e) {
					throw new IllegalException("source object is illegal");
				}
			} else {
				throw new IllegalException("source object is illegal");
			}
		}
		process0(sourceModel, new HashMap<String, Field>(), new HashMap<String, Model>(), jsonNode);
		return sourceModel;
	}

	private void process0(Model sourceModel, final Map<String, Field> fieldPathMap, Map<String, Model> modelPathMap,
			JsonNode jsonNode) {
		if (sourceModel != null) {
			sourceModel.setModelPathMap(modelPathMap);
			sourceModel.setFieldPathMap(fieldPathMap);
			if (sourceModel instanceof Entity) {
				if (sourceModel.getModelPath() == null) {
					// root
					sourceModel.setFieldPathMap(fieldPathMap);
					sourceModel.setModelPath(sourceModel.getName());
				}

				List<Field> fields = ((Entity) sourceModel).getFields();
				if (fields != null) {
					for (Field field : fields) {
						// get value from model instance, then
						JsonNode node = jsonNode.path(field.getName());
						field.setValue(node.asText());
						sourceModel.getFieldPathMap().put(sourceModel.getModelPath() + "." + field.getName(), field);
					}
				}
			}

			modelPathMap.put(sourceModel.getModelPath(), sourceModel);

			List<Model> subModels = sourceModel.getSubModels();
			if (subModels != null) {
				for (Model subModel : subModels) {
					subModel.setFieldPathMap(fieldPathMap);
					if (subModel instanceof Array) {
						subModel.setModelPath(sourceModel.getModelPath() + "." + subModel.getName() + "[]");
					} else {
						subModel.setModelPath(sourceModel.getModelPath() + "." + subModel.getName());
					}
					JsonNode subJsonNode = jsonNode.path(subModel.getName());
					if (subModel instanceof Entity) {
						if (!subJsonNode.isMissingNode()) {
							process0(subModel, fieldPathMap, modelPathMap, subJsonNode);
						}
					} else if (subModel instanceof Array) {
						modelPathMap.put(subModel.getModelPath(), subModel);
						subModel.setModelPathMap(modelPathMap);
						subModel.setFieldPathMap(fieldPathMap);
						List<Model> subSubModels = subModel.getSubModels();

						if (subSubModels != null) {
							Model subSubModel = subSubModels.get(0);

							for (int i = 0; i < subJsonNode.size(); i++) {
								JsonNode subSubJsonNode = subJsonNode.get(i);

								Model clonedSubSubModel = null;
								if (i == 0) {
									clonedSubSubModel = subSubModel;
								} else {
									clonedSubSubModel = subSubModel.clone();
									subModel.getSubModels().add(clonedSubSubModel);
								}
								clonedSubSubModel.setModelPath(
										sourceModel.getModelPath() + "." + clonedSubSubModel.getName() + "[" + i + "]");
								clonedSubSubModel.setFieldPathMap(fieldPathMap);

								process0(clonedSubSubModel, fieldPathMap, modelPathMap, subSubJsonNode);
							}
						}
					}
				}
			}
		}
	}
}
