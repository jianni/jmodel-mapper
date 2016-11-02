package com.github.jmodel.mapper.impl;

import java.util.List;
import java.util.Map;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.api.ModeEnum;
import com.github.jmodel.mapper.api.Model;

public abstract class AbstractAnalyzer<T> implements Analyzer {

	protected void populateInstance(final ModeEnum mode, final Model sourceModel, final Map<String, Field> fieldPathMap,
			final Map<String, Model> modelPathMap, final T node) {
		if (mode == null || mode == ModeEnum.MANUAL) {
			populateInstanceManually(sourceModel, fieldPathMap, modelPathMap, node);
		} else if (mode == ModeEnum.AUTO) {
			populateInstanceAutomatically(sourceModel, fieldPathMap, modelPathMap, "", node);
		} else {
			throw new IllegalException("wrong mode");
		}
	}

	/**
	 * If mapping mode is auto, model will be built base on source object.
	 * 
	 * @param sourceModel
	 * @param fieldPathMap
	 * @param modelPathMap
	 * @param node
	 */
	protected abstract void populateInstanceAutomatically(final Model sourceModel,
			final Map<String, Field> fieldPathMap, final Map<String, Model> modelPathMap, final String nodeName,
			final T node);

	/**
	 * If mapping mode is manual, source model template is built base on the raw
	 * source field path which is defined in mapping file. This method should be
	 * called when need to set value of fields in source model instance.
	 * 
	 * @param sourceModel
	 * @param fieldPathMap
	 * @param modelPathMap
	 * @param node
	 */
	protected void populateInstanceManually(final Model sourceModel, final Map<String, Field> fieldPathMap,
			final Map<String, Model> modelPathMap, final T node) {
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
							populateInstanceManually(subModel, fieldPathMap, modelPathMap, subNode);
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

	/**
	 * Different ways to set value for different model, e.g. JSON, XML
	 * 
	 * @param sourceNode
	 * @param field
	 */
	protected abstract void setFieldValue(final T sourceNode, final Field field);

	protected abstract T getSubNode(final T node, final String subNodeName);

	protected abstract void populateSubModel(final T subNode, final Model subModel, final Model subSubModel);

}
