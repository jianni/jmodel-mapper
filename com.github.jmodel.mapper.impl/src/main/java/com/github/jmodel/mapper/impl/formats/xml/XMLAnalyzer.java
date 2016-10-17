package com.github.jmodel.mapper.impl.formats.xml;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.jmodel.mapper.api.Analyzer;
import com.github.jmodel.mapper.api.Array;
import com.github.jmodel.mapper.api.Entity;
import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.Model;

public class XMLAnalyzer implements Analyzer {

	public Model process(Model sourceModel, Object sourceObject) {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// parse an XML file into a DOM tree
			Document document = builder.parse(new ByteArrayInputStream(((String) sourceObject).getBytes()));
			process0(sourceModel, document.getDocumentElement(), new HashMap<String, Field>(),
					new HashMap<String, Model>());
			return sourceModel;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private void process0(Model sourceModel, Element node, final Map<String, Field> fieldPathMap,
			Map<String, Model> modelPathMap) {
		if (sourceModel != null) {
			Model parent = sourceModel.getParentModel();
			sourceModel.setModelPathMap(modelPathMap);
			sourceModel.setFieldPathMap(fieldPathMap);
			if (sourceModel.getModelPath() == null) {
				sourceModel.setModelPath(sourceModel.getName());
			}
			modelPathMap.put(sourceModel.getModelPath(), sourceModel);

			if (sourceModel instanceof Entity) {
				NodeList subNodeList = node.getChildNodes();
				List<Field> fields = ((Entity) sourceModel).getFields();
				if (fields != null) {
					for (Field field : fields) {
						// get value from model instance, then
						String name = field.getName();
						int i = name.indexOf("@");
						if (i >= 0) {
							name = name.substring(i + 1);
						}
						NodeList children = node.getElementsByTagName(name);
						if (children != null && children.getLength() == 1) {
							field.setValue(children.item(0).getTextContent());
						}
						fieldPathMap.put(sourceModel.getModelPath() + "." + field.getName(), field);
					}
				}
			}

			List<Model> childrenModel = sourceModel.getSubModels();
			if (childrenModel != null) {
				for (int i = 0; i < childrenModel.size(); i++) {
					Model childModel = childrenModel.get(i);
					if (childModel instanceof Entity) {
						NodeList childrenNode = node.getElementsByTagName(childModel.getName());
						childModel.setModelPath(sourceModel.getModelPath() + "." + childModel.getModelPath());
						process0(childModel, (Element) childrenNode.item(0), fieldPathMap, modelPathMap);
					} else if (childModel instanceof Array) {
						childModel.setModelPath(sourceModel.getModelPath() + "." + childModel.getModelPath());
						modelPathMap.put(childModel.getModelPath(), childModel);
						childModel.setModelPathMap(modelPathMap);
						childModel.setFieldPathMap(fieldPathMap);
						List<Model> subSubModels = childModel.getSubModels();
						if (subSubModels != null) {
							Model subSubModel = subSubModels.get(0);
							NodeList childrenNode = node
									.getElementsByTagName(StringUtils.substringBefore(childModel.getName(), "[]"));
							int k = -1;
							for (int j = 0; j < childrenNode.getLength(); j++) {
								Node subNode = childrenNode.item(j);
								if (subNode.getParentNode() == node) {
									k++;
									Model clonedSubSubModel = null;
									if (k == 0) {
										clonedSubSubModel = subSubModel;
									} else {
										clonedSubSubModel = subSubModel.clone();
										clonedSubSubModel.setModelPath(subNode.getNodeName() + "[" + k + "]");
									}
									process0(clonedSubSubModel, (Element) subNode, fieldPathMap, modelPathMap);
								}
							}
						}
					}
				}
			}
		}
	}

}
