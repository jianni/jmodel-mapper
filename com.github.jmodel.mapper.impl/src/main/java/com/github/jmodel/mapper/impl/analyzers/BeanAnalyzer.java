package com.github.jmodel.mapper.impl.analyzers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.jmodel.mapper.api.Field;
import com.github.jmodel.mapper.api.IllegalException;
import com.github.jmodel.mapper.api.Model;
import com.github.jmodel.mapper.impl.AbstractAnalyzer;

public class BeanAnalyzer extends AbstractAnalyzer<Object> {

	@Override
	public <T> Model process(Model sourceModel, T sourceObject) throws IllegalException {
		populateModel(sourceModel, new HashMap<String, Field>(), new HashMap<String, Model>(), sourceObject);
		return sourceModel;
	}

	@Override
	protected void setFieldValue(Object sourceNode, Field field) {
		try {
			Method m = sourceNode.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
			field.setValue((String) m.invoke(sourceNode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Object getSubNode(Object node, String subNodeName) {
		try {
			Method m = node.getClass().getMethod("get" + StringUtils.capitalize(subNodeName));
			return m.invoke(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void populateSubModel(Object subNode, Model subModel, Model subSubModel) {
		if (subNode instanceof List<?>) {
			List<?> subNodeList = (List<?>) subNode;
			for (int i = 0; i < subNodeList.size(); i++) {
				Object subSubJsonNode = subNodeList.get(i);

				Model clonedSubSubModel = null;
				if (i == 0) {
					clonedSubSubModel = subSubModel;
				} else {
					clonedSubSubModel = subSubModel.clone();
					subModel.getSubModels().add(clonedSubSubModel);
				}
				clonedSubSubModel
						.setModelPath(subModel.getModelPath() + "." + clonedSubSubModel.getName() + "[" + i + "]");
				clonedSubSubModel.setFieldPathMap(subModel.getFieldPathMap());

				populateModel(clonedSubSubModel, subModel.getFieldPathMap(), subModel.getModelPathMap(),
						subSubJsonNode);
			}
		}

	}

}