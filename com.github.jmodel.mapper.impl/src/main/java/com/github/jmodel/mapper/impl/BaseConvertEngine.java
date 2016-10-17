package com.github.jmodel.mapper.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.AnalyzerFactory;
import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Builder;
import com.github.jmodel.mapper.api.BuilderFactory;
import com.github.jmodel.mapper.api.Engine;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.FormatChecker;
import com.github.jmodel.mapper.api.FormatCheckerFactory;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.api.Mapping;
import com.github.jmodel.mapper.api.Model;

public class BaseConvertEngine implements Engine {

	protected FormatCheckerFactory formatCheckerFactory = new FormatCheckerFactoryImpl();

	protected AnalyzerFactory analyzerFactory = new AnalyzerFactoryImpl();

	protected BuilderFactory builderFactory = new BuilderFactoryImpl();

	private static String DEFAULT_PACKAGE_NAME = "com.github.jmodel.mapper";

	private static String NAME_PATTERN = "^([a-zA-Z0-9]\\d*(\\.[a-zA-Z0-9])?)+$";

	public <T, R> R convert(T sourceObj, String mappingURI) throws IllegalException {
		if (sourceObj == null) {
			throw new IllegalException("source object is null");
		}
		if (mappingURI == null || !Pattern.matches(NAME_PATTERN, mappingURI)) {
			throw new IllegalException("mappingName is illegal");
		}
		// TODO consider more loading mechanism later, local or remote
		Class<?> mappingClz;
		try {
			mappingClz = Class.forName(mappingURI);
		} catch (ClassNotFoundException e) {
			throw new IllegalException("mapping is not exists");
		}

		Mapping mapping;
		try {
			Method method = mappingClz.getMethod("getInstance");
			mapping = (Mapping) (method.invoke(null));
		} catch (Exception e) {
			throw new IllegalException("mapping content is illegal");
		}
		if (mapping == null) {
			throw new IllegalException("mapping is found but not avaliable");
		}

		FormatChecker formatChecker = formatCheckerFactory.createFormatChecker(mapping.getFromFormat());
		if (!formatChecker.isValid(sourceObj)) {
			throw new IllegalException("format of source is not supported");
		}

		Model sourceTemplateModel = mapping.getSourceTemplateModel();
		Model targetTemplateModel = mapping.getTargetTemplateModel();
		if (!mapping.isTemplateReady()) {
			populateModel(sourceTemplateModel, mapping.getRawSourceFieldPaths());
			populateModel(targetTemplateModel, mapping.getRawTargetFieldPaths());
			mapping.setTemplateReady(true);
		}

		Analyzer analyzer = analyzerFactory.createAnalyzer(mapping.getFromFormat());
		Model sourceModel = analyzer.process(sourceTemplateModel.clone(), sourceObj);
		Model targetModel = targetTemplateModel.clone();
		fillTargetInstance(targetModel, new HashMap<String, Field>(), new HashMap<String, Model>());
		mapping.execute(sourceModel, targetModel);
		cleanUnusedField(targetModel);
		Builder builder = builderFactory.createBuilder(mapping.getToFormat());
		return (R) builder.process(targetModel);
	}

	private void fillTargetInstance(Model targetInstanceModel, Map<String, Field> filePathMap,
			Map<String, Model> modelPathMap) {
		if (targetInstanceModel != null) {
			targetInstanceModel.setFieldPathMap(filePathMap);
			targetInstanceModel.setModelPathMap(modelPathMap);

			if (targetInstanceModel.getModelPath() == null) {
				targetInstanceModel.setModelPath(targetInstanceModel.getName());
			}

			modelPathMap.put(targetInstanceModel.getModelPath(), targetInstanceModel);

			if (targetInstanceModel instanceof Entity) {
				List<Field> fieldList = ((Entity) targetInstanceModel).getFields();
				if (fieldList != null) {
					for (Field field : fieldList) {
						field.setParentEntity((Entity) targetInstanceModel);
						filePathMap.put(targetInstanceModel.getModelPath() + "." + field.getName(), field);
					}
				}
			}

			List<Model> subModelList = targetInstanceModel.getSubModels();
			if (subModelList != null) {
				for (Model subModel : subModelList) {
					if (subModel instanceof Entity) {
						subModel.setModelPath(subModel.getParentModel().getModelPath() + "." + subModel.getName());
						fillTargetInstance(subModel, filePathMap, modelPathMap);
					}

					if (subModel instanceof Array) {
						subModel.setModelPath(targetInstanceModel.getModelPath() + "." + subModel.getName() + "[]");
						subModel.setModelPathMap(modelPathMap);
						subModel.setFieldPathMap(filePathMap);
						modelPathMap.put(subModel.getModelPath(), subModel);
						List<Model> subSubModelList = subModel.getSubModels();
						for (int i = 0; i < subSubModelList.size(); i++) {
							Model entityInArray = subSubModelList.get(i);
							entityInArray.setModelPath(
									targetInstanceModel.getModelPath() + "." + subModel.getName() + "[" + i + "]");
							fillTargetInstance(entityInArray, filePathMap, modelPathMap);
						}
					}
				}
			}
		}
	}

	private void cleanUnusedField(Model model) {
		if (model instanceof Entity) {
			Entity currentEntity = (Entity) model;
			List<Field> newFieldList = new ArrayList<Field>();
			List<Field> fieldList = currentEntity.getFields();
			if (fieldList != null) {
				for (Field field : fieldList) {
					if (field.isUsed()) {
						newFieldList.add(field);
					}
				}
			}
			currentEntity.setFields(newFieldList);
		}

		List<Model> subModelList = model.getSubModels();
		if (subModelList != null) {
			for (Model subModel : subModelList) {
				cleanUnusedField(subModel);
			}
		}
	}

	private void populateModel(final Model root, final List<String> fieldPaths) {
		final Map<String, Object> modelOrFieldMap = new HashMap<String, Object>();

		for (String fieldPath : fieldPaths) {

			String[] paths = fieldPath.split("\\.");

			String currentPath = "";
			String parentPath = "";

			for (int i = 0; i < paths.length - 1; i++) {

				if (parentPath.equals("")) {
					currentPath = paths[i];
				} else {
					currentPath = parentPath.replace("[]", "[0]") + "." + paths[i];
				}

				Model currentModel = (Model) modelOrFieldMap.get(currentPath);
				if (currentModel == null) {
					// create model object
					if (parentPath.equals("")) {
						currentModel = root;
					} else {
						if (paths[i].indexOf("[]") != -1) {
							currentModel = new ArrayImpl();
							currentModel.setName(StringUtils.substringBefore(paths[i], "[]"));
						} else {
							currentModel = new EntityImpl();
							currentModel.setName(paths[i]);
						}
					}
					if (currentModel.getName() == null) {
						currentModel.setName(paths[i]);
					}
					currentModel.setModelPath(currentPath);
					modelOrFieldMap.put(currentPath, currentModel);

					// if current Model is Array,create a existing entity
					if (currentModel instanceof Array) {
						Entity subEntity = new EntityImpl();
						String entityName = StringUtils.substringBefore(paths[i], "[]");
						subEntity.setName(entityName);
						currentPath = currentPath.replace("[]", "[0]");
						subEntity.setModelPath(currentPath);
						subEntity.setParentModel(currentModel);
						currentModel.getSubModels().add(subEntity);
						modelOrFieldMap.put(currentPath, subEntity);

					}

					// maintenence parent model relation
					Model parentModel = (Model) modelOrFieldMap.get(parentPath.replaceAll("\\[\\]", "\\[0\\]"));
					if (parentModel != null) {
						currentModel.setParentModel(parentModel);
						List<Model> subModelList = parentModel.getSubModels();
						if (subModelList == null) {
							subModelList = new ArrayList<Model>();
							parentModel.setSubModels(subModelList);
						}
						subModelList.add(currentModel);

					}

				}
				// set parentPath
				parentPath = currentPath;
			}

			// set field list
			String fieldName = paths[paths.length - 1];
			if (!fieldName.equals("_")) {
				currentPath = currentPath + "." + fieldName;
				Field currentField = (Field) modelOrFieldMap.get(currentPath);
				if (currentField == null) {
					currentField = new FieldImpl();
					currentField.setName(fieldName);
					modelOrFieldMap.put(currentPath, currentField);
					Entity currentModel = null;
					Object model = modelOrFieldMap.get(parentPath);
					if (model instanceof Entity) {
						currentModel = (Entity) model;
					} else if (model instanceof Array) {
						Array aModel = (Array) model;
						currentModel = (Entity) aModel.getSubModels().get(0);
					}
					List<Field> fieldList = currentModel.getFields();
					if (fieldList == null) {
						fieldList = new ArrayList<Field>();
						currentModel.setFields(fieldList);
					}
					fieldList.add(currentField);
				}
			}

		}
	}

}
