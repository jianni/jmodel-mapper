package com.github.jmodel.mapper.api;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ModelHelper {

	public static void buildRelationForSubModel(Model parentModel, Model subModel) {
		subModel.setParentModel(parentModel);
		subModel.setFieldPathMap(parentModel.getFieldPathMap());
		subModel.setModelPathMap(parentModel.getModelPathMap());

		String parentModelPath = parentModel.getModelPath();
		if (subModel instanceof Entity) {
			if (parentModel instanceof Array) {
				subModel.setModelPath(parentModelPath.substring(0, parentModelPath.lastIndexOf("[")) + "["
						+ (parentModel.getSubModels().size() - 1) + "]");
			} else {
				subModel.setModelPath(parentModelPath + "." + subModel.getName());
			}
			for (Field field : ((Entity) subModel).getFields()) {
				subModel.getFieldPathMap().put(subModel.getModelPath() + "." + field.getName(), field);
			}
		} else {
			subModel.setModelPath(parentModelPath + "." + subModel.getName() + "[]");
		}

		subModel.getModelPathMap().put(subModel.getModelPath(), subModel);

		List<Model> subSubModels = subModel.getSubModels();
		if (subSubModels != null) {
			for (Model subSubModel : subSubModels) {
				buildRelationForSubModel(subModel, subSubModel);
			}
		}
	}

	public static <T> void arrayMapping(final Model rootSourceModel, final Model rootTargetModel,
			final Model mySourceModel, Model myTargetModel, final String sourceModelPath, final String targetModelPath,
			Integer targetIndex, final Predicate<String> p, final Consumer<T> c) {
		if (mySourceModel == null) {
			return;
		}
		arrayMapping(rootSourceModel, rootTargetModel, mySourceModel, myTargetModel, sourceModelPath, targetModelPath,
				targetIndex, false, p, c);
	}

	public static <T> void arrayMapping(final Model rootSourceModel, final Model rootTargetModel,
			final Model mySourceModel, Model myTargetModel, final String sourceModelPath, final String targetModelPath,
			Integer targetIndex, final boolean isAppend, final Predicate<String> p, final Consumer<T> c) {

		String[] modelPath = new String[3];
		modelPath[0] = sourceModelPath;
		modelPath[1] = targetModelPath;
		modelPath[2] = targetIndex + "";

		doIt(modelPath, mySourceModel, myTargetModel, sourceModelPath, targetModelPath, isAppend, p, c);
	}

	@SuppressWarnings("unchecked")
	private static <T> void doIt(final String[] modelPath, final Model mySourceModel, final Model myTargetModel,
			final String sourceModelPath, final String targetModelPath, final boolean isAppend,
			final Predicate<String> p, final Consumer<T> c) {

		if (modelPath[2] == null || modelPath[2].trim().length() == 0) {
			modelPath[2] = "0";
		}

		if (mySourceModel instanceof Entity) {
			modelPath[0] = sourceModelPath;
			if (myTargetModel instanceof Array) {
				// modelPath[1] =
				// myTargetModel.getSubModels().get(0).getModelPath();

				Entity targetEntityModel = (Entity) myTargetModel.getSubModels().get(0);
				if (!targetEntityModel.isUsed()) {
					modelPath[1] = myTargetModel.getSubModels().get(0).getModelPath();
				} else {
					if (isAppend) {

						targetEntityModel = (Entity) targetEntityModel.clone();
						myTargetModel.getSubModels().add(targetEntityModel);
						ModelHelper.buildRelationForSubModel(myTargetModel, targetEntityModel);
						modelPath[1] = targetEntityModel.getModelPath();
						modelPath[2] = (myTargetModel.getSubModels().size() - 1) + "";

					} else {
						modelPath[1] = myTargetModel.getSubModels().get(Integer.valueOf(modelPath[2])).getModelPath();
					}
				}
				targetEntityModel.setUsed(true);
			} else {
				modelPath[1] = targetModelPath;
			}
			if (p != null && !p.test(modelPath[0])) {
				// not pass the condition
				return;
			}

			c.accept((T) modelPath);
			return;
		}

		List<Model> subSourceEntities = mySourceModel.getSubModels();

		for (Model subSourceEntity : subSourceEntities) {
			modelPath[0] = subSourceEntity.getModelPath();
			if (modelPath[0] == null) { // no data in source model
				myTargetModel.getParentModel().getSubModels().remove(myTargetModel);
				break;
			}
			if (p != null && !p.test(modelPath[0])) {
				// not pass the condition
				continue;
			}
			if (myTargetModel instanceof Entity) {
				modelPath[1] = myTargetModel.getModelPath();
				c.accept((T) modelPath);
			} else {
				Entity targetEntityModel = (Entity) myTargetModel.getSubModels().get(0);
				if (!targetEntityModel.isUsed()) {
					modelPath[1] = myTargetModel.getSubModels().get(0).getModelPath();
				} else {
					if (isAppend || (!isAppend
							&& myTargetModel.getSubModels().size() < mySourceModel.getSubModels().size())) {

						targetEntityModel = (Entity) targetEntityModel.clone();
						myTargetModel.getSubModels().add(targetEntityModel);
						ModelHelper.buildRelationForSubModel(myTargetModel, targetEntityModel);
						modelPath[1] = targetEntityModel.getModelPath();
						modelPath[2] = (myTargetModel.getSubModels().size() - 1) + "";

					} else {
						modelPath[1] = myTargetModel.getSubModels().get(Integer.valueOf(modelPath[2])).getModelPath();
					}
				}
				targetEntityModel.setUsed(true);
				c.accept((T) modelPath);
			}
		}
	}

	public static <T1, T2> String calc(final T1 leftOperant, final T2 rightOperant, final OperationEnum operation,
			final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle("com.github.jmodel.mapper.api.MessagesBundle",
				currentLocale);
		switch (operation) {
		case PLUS: {
			if (leftOperant instanceof String || rightOperant instanceof String) {
				return String.valueOf(leftOperant) + String.valueOf(rightOperant);
			} else if (leftOperant instanceof Integer && rightOperant instanceof Integer) {
				return String.valueOf((Integer) leftOperant + (Integer) rightOperant);
			}
			return null;
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "xxx"));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Boolean predict(final Comparable leftOperant, final Comparable rightOperant,
			final OperationEnum operation, final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle("com.github.jmodel.mapper.api.MessagesBundle",
				currentLocale);
		if (leftOperant == null || rightOperant == null) {
			switch (operation) {
			case EQUALS: {
				return leftOperant == rightOperant;
			}
			case NOTEQUALS: {
				return leftOperant != rightOperant;
			}
			default:
				throw new IllegalException(
						String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "NULL"));
			}
		}
		int compareResult = leftOperant.compareTo(rightOperant);
		switch (operation) {
		case EQUALS: {
			return compareResult == 0 ? true : false;
		}
		case NOTEQUALS: {
			return compareResult != 0 ? true : false;
		}
		case GT: {
			return compareResult > 0 ? true : false;
		}
		case GTE: {
			return (compareResult > 0 || compareResult == 0) ? true : false;
		}
		case LT: {
			return compareResult < 0 ? true : false;
		}
		case LTE: {
			return (compareResult < 0 || compareResult == 0) ? true : false;
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "Comparable"));
		}
	}

	public static Boolean predict(final String leftOperant, final List<String> rightOperant,
			final OperationEnum operation, final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle("com.github.jmodel.mapper.api.MessagesBundle",
				currentLocale);
		switch (operation) {
		case IN: {
			return rightOperant.contains(leftOperant);
		}
		case NOTIN: {
			return !rightOperant.contains(leftOperant);
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "String and List"));
		}

	}

	public static Boolean predict(final Boolean leftOperant, final Boolean rightOperant, final OperationEnum operation,
			final Locale currentLocale) {
		ResourceBundle messages = ResourceBundle.getBundle("com.github.jmodel.mapper.api.MessagesBundle",
				currentLocale);
		switch (operation) {
		case OR: {
			return Boolean.logicalOr(leftOperant, rightOperant);
		}
		case AND: {
			return Boolean.logicalAnd(leftOperant, rightOperant);
		}
		default:
			throw new IllegalException(
					String.format(currentLocale, messages.getString("OPR_NOT_SUPPORT"), operation, "Boolean"));
		}
	}

}
