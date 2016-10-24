package com.github.jmodel.mapper.impl;

import java.util.List;
import java.util.Map;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.Model;

public abstract class AbstractAnalyzer<T> implements Analyzer {

	protected void populateModel(Model sourceModel, final Map<String, Field> fieldPathMap,
			Map<String, Model> modelPathMap, T node) {
		if (sourceModel != null) {
			sourceModel.setModelPathMap(modelPathMap);
			sourceModel.setFieldPathMap(fieldPathMap);
			if (sourceModel instanceof Entity) {
				if (sourceModel.getModelPath() == null) {
					sourceModel.setFieldPathMap(fieldPathMap);
					sourceModel.setModelPath(sourceModel.getName());
				}

				List<Field> fields = ((Entity) sourceModel).getFields();
				if (fields != null) {
					for (Field field : fields) {
						setFieldValue(node, field);
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

					T subNode = getSubNode(node, subModel.getName());

					if (subModel instanceof Entity) {
						if (subNode != null) {
							populateModel(subModel, fieldPathMap, modelPathMap, subNode);
						}
					} else if (subModel instanceof Array) {
						modelPathMap.put(subModel.getModelPath(), subModel);
						subModel.setModelPathMap(modelPathMap);
						subModel.setFieldPathMap(fieldPathMap);
						List<Model> subSubModels = subModel.getSubModels();

						if (subSubModels != null) {
							Model subSubModel = subSubModels.get(0);
							populateSubModel(subNode, subModel, subSubModel);
						}
					}
				}
			}
		}
	}

	protected abstract void setFieldValue(T sourceNode, Field field);

	protected abstract T getSubNode(T node, String subNodeName);

	protected abstract void populateSubModel(T subNode, Model subModel, Model subSubModel);
}
