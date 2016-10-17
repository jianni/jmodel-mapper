package com.github.jmodel.mapper.plugin.jvmmodel

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xbase.XAbstractFeatureCall
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XMemberFeatureCall
import java.util.Stack
import org.eclipse.xtext.xbase.XIfExpression
import com.github.jmodel.mapper.plugin.mappingLanguage.Filter
import com.github.jmodel.mapper.plugin.mappingLanguage.MappingBlock
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathXFeatureCall
import com.github.jmodel.mapper.plugin.mappingLanguage.Body

class Util {

	def static boolean isDot(String x) {
		if (x.equals(".")) {
			true
		} else {
			false
		}
	}

	def static boolean isInFilter(EObject x) {
		if (x instanceof Filter) {
			return true;
		} else if (x instanceof MappingBlock) {
			return false;
		} else {
			return isInFilter(x.eContainer);
		}
	}

	def static boolean isInAppendPath(EObject x) {
		if (x instanceof Body) {
			return false;
		} else if (x instanceof MappingBlock) {
			if (x.isAppend != null) {
				return true;
			}
		}
		return isInAppendPath(x.eContainer);
	}

	def static boolean isInXIfExpression(EObject x) {
		if (x instanceof XIfExpression) {
			return true;
		} else if (x instanceof MappingBlock) {
			return false;
		} else {
			return isInFilter(x.eContainer);
		}
	}

	/**
	 * If the EObject is in a array path, include self, then return true 
	 */
	def static boolean isArrayPath(EObject eObj) {
		val sourceModelPath = getFullModelPath(eObj, true)
		val targetModelPath = getFullModelPath(eObj, false)
		sourceModelPath.contains("[") || targetModelPath.contains("[")
	}

	/**
	 * If the EObject is in a array path, not include self, then return true 
	 */
	def static boolean isInArrayPath(EObject eObj) {
		isSourceModelInArrayPath(eObj) || isTargetModelInArrayPath(eObj)
	}

	def private static boolean isSourceModelInArrayPath(EObject eObj) {
		val sourceModelPath = getFullModelPath(eObj, true)
		val lastDotPosition = sourceModelPath.lastIndexOf(".");
		if (lastDotPosition < 0) {
			false
		} else {
			sourceModelPath.substring(0, lastDotPosition).contains("[")
		}
	}

	def private static boolean isTargetModelInArrayPath(EObject eObj) {
		val targetModelPath = getFullModelPath(eObj, false)
		val lastDotPosition = targetModelPath.lastIndexOf(".");
		if (lastDotPosition < 0) {
			false
		} else {
			targetModelPath.substring(0, lastDotPosition).contains("[")
		}
	}

	/**
	 * If the EObject is in a array 
	 */
	def static boolean isArray(EObject eObj) {
		isSourceModelArray(eObj) || isTargetModelArray(eObj)
	}

	def private static boolean isSourceModelArray(EObject eObj) {
		val sourceModelPath = getFullModelPath(eObj, true)
		val lastDotPosition = sourceModelPath.lastIndexOf(".");
		if (lastDotPosition < 0) {
			sourceModelPath.contains("[")
		} else {
			sourceModelPath.substring(lastDotPosition).contains("[")
		}
	}

	def private static boolean isTargetModelArray(EObject eObj) {
		val targetModelPath = getFullModelPath(eObj, false)
		val lastDotPosition = targetModelPath.lastIndexOf(".");
		if (lastDotPosition < 0) {
			targetModelPath.contains("[")
		} else {
			targetModelPath.substring(lastDotPosition).contains("[")
		}
	}

	def static void buildMappingBlockStack(Stack<MappingBlock> mappingBlockStack, EObject eObj) {
		if (eObj instanceof MappingBlock) {
			mappingBlockStack.push(eObj)
			if (eObj.eContainer instanceof Body) {
				return
			}
		}
		buildMappingBlockStack(mappingBlockStack, eObj.eContainer)
	}

	def static String getSourceModelPathByPath(EObject eObj) {
		getFullModelPath(getMappingBlockByPath(eObj), true)
	}

	def static String getTargetModelPathByPath(EObject eObj) {
		getFullModelPath(getMappingBlockByPath(eObj), false)
	}

	def static MappingBlock getMappingBlockByPath(EObject eObj) {

		val Stack<MappingBlock> mappingBlockStack = new Stack<MappingBlock>()
		buildMappingBlockStack(mappingBlockStack, eObj)
		if (eObj instanceof MappingBlock) {
			getMappingBlockByPath0(mappingBlockStack, eObj.isFollowRootPath.length)
		} else if (eObj instanceof SourceFieldPathXFeatureCall) {
			getMappingBlockByPath0(mappingBlockStack, eObj.isFollowRootPath.length)
		} else if (eObj instanceof Body) {
			throw new RuntimeException("please contact system administrator");
		} else {
			getMappingBlockByPath(eObj.eContainer);
		}
	}

	def static MappingBlock getMappingBlockByPath0(Stack<MappingBlock> mappingBlockStack, int index) {
		if (index == 1) {
			mappingBlockStack.pop
		} else {
			mappingBlockStack.pop
			getMappingBlockByPath0(mappingBlockStack, index - 1)
		}
	}

	def static MappingBlock getCurrentMappingBlock(EObject eObj) {
		if (eObj instanceof MappingBlock) {
			return eObj
		} else {
			return getCurrentMappingBlock(eObj.eContainer)
		}
	}

	def static MappingBlock getCurrentAliasedMappingBlockForTargetModelPath(EObject eObj) {
		if (eObj instanceof MappingBlock && !isDot((eObj as MappingBlock).targetModelPath)) {
			return eObj as MappingBlock
		} else {
			return getCurrentAliasedMappingBlockForTargetModelPath(eObj.eContainer)
		}
	}

	def static MappingBlock getAppendedMappingBlock(Body mappingBlocks, String fullTargetModelPath) {
		for (mappingBlock : mappingBlocks.mappingBlocks) {
			val appendedMappingBlock = getAppendedMappingBlock0(mappingBlock as MappingBlock, fullTargetModelPath)
			if (appendedMappingBlock != null) {
				return appendedMappingBlock
			}
		}
		return null
	}

	def private static MappingBlock getAppendedMappingBlock0(MappingBlock mappingBlock, String fullTargetModelPath) {
		if (!(mappingBlock.targetModelPath.equals(".")) && mappingBlock.isAppend == null &&
			fullTargetModelPath.equals(getFullModelPath(mappingBlock, false))) {
			return mappingBlock
		} else {
			for (subMappingBlock : mappingBlock.mappingBlocks) {
				return getAppendedMappingBlock0(subMappingBlock as MappingBlock, fullTargetModelPath)
			}
			return null
		}
	}

	def static MappingBlock getLastArrayMappingBlock(EObject eObj, boolean isOfSourceModel) {
		getLastArrayMappingBlock0(eObj, isOfSourceModel, 0);
	}

	def private static MappingBlock getLastArrayMappingBlock0(EObject eObj, boolean isOfSourceModel, int found) {
		if (eObj instanceof Body) {
			return null;
		} else if (eObj instanceof MappingBlock) {

			if (isOfSourceModel && eObj.isFollowRootPath != null) {
				return getMappingBlockByPath(eObj); // just for source model
			}
			if (Util.isArray(eObj)) {
				if (found == 1) {
					return eObj
				} else {
					if (!isOfSourceModel && Util.isInAppendPath(eObj)) {
						return getLastArrayMappingBlock0(eObj.eContainer, isOfSourceModel, found)
					} else {
						return getLastArrayMappingBlock0(eObj.eContainer, isOfSourceModel, found + 1)
					}
				}
			}
		}
		return getLastArrayMappingBlock0(eObj.eContainer, isOfSourceModel, found)

	}

	def static Body getMappingBlocks(EObject eObj) {
		if (eObj instanceof Body) {
			return eObj
		} else {
			return getMappingBlocks(eObj.eContainer)
		}
	}

	def static String getCurrentSourceModelPath(EObject eObj) {
		if (eObj instanceof MappingBlock) {
			if (!(eObj.sourceModelPath.equals("."))) {
				return eObj.sourceModelPath
			}
		}
		getCurrentSourceModelPath(eObj.eContainer)
	}

	def static String getCurrentTargetModelPath(EObject eObj) {
		if (eObj instanceof MappingBlock) {
			if (!(eObj.targetModelPath.equals("."))) {
				return eObj.targetModelPath
			}
		}
		getCurrentTargetModelPath(eObj.eContainer)
	}

	// get source model path of last array, if no array, return null
	def static String[] getLastArrayModelPath(EObject eObj, boolean isSourceModelPath) {
		var String[] modelPaths = newArrayOfSize(2)
		var String modelPath = null
		if (isSourceModelPath) {
			modelPath = getFullModelPath(eObj, true)
		} else {
			modelPath = getFullModelPath(eObj, false)
		}

		val lastDotPosition = modelPath.lastIndexOf(".");
		if (lastDotPosition < 0) {
			return null
		} else {
			val parentModelPath = modelPath.substring(0, lastDotPosition)
			val lastArrayPosition = parentModelPath.lastIndexOf("[")
			if (lastArrayPosition < 0) {
				return null
			} else {
				modelPaths.set(0, parentModelPath.substring(0, lastArrayPosition) + "[]")
				modelPaths.set(1, modelPath.substring(lastArrayPosition + 2))
				return modelPaths
			}
		}
	}

	/**
	 * 
	 */
	def static String getFullModelPath(EObject eObj, boolean isSourceModelPath) {
		val currentMappingBlock = getCurrentMappingBlock(eObj)
		if (currentMappingBlock.eContainer instanceof Body) {
			if (isSourceModelPath) {
				return currentMappingBlock.sourceModelPath
			} else {
				return currentMappingBlock.targetModelPath
			}
		}
		return getFullModelPath0(currentMappingBlock, "", isSourceModelPath)
	}

	def private static String getFullModelPath0(EObject eObj, String modelPath, boolean isSourceModelPath) {
		if (eObj instanceof Body) {
			return modelPath;
		}

		if (!(eObj instanceof MappingBlock)) {
			getFullModelPath0(eObj.eContainer, modelPath, isSourceModelPath)
		}

		val MappingBlock mappingBlock = (eObj as MappingBlock)

		if (isSourceModelPath) {
			if (mappingBlock.isFollowRootPath != null) {
				if (mappingBlock.sourceModelPath.equals(".")) {
					return getSourceModelPathByPath(mappingBlock)
				} else {
					return getSourceModelPathByPath(mappingBlock) + '.' + mappingBlock.sourceModelPath
				}
			} else {
				if (mappingBlock.sourceModelPath.equals(".")) {
					getFullModelPath0(eObj.eContainer, modelPath, isSourceModelPath)
				} else {
					if (modelPath.trim.length == 0) {
						getFullModelPath0(eObj.eContainer, mappingBlock.sourceModelPath, isSourceModelPath)
					} else {
						getFullModelPath0(eObj.eContainer, mappingBlock.sourceModelPath + '.' + modelPath,
							isSourceModelPath)
					}

				}
			}
		} else {
			if (mappingBlock.targetModelPath.equals(".")) {
				getFullModelPath0(eObj.eContainer, modelPath, isSourceModelPath)
			} else {
				if (modelPath.trim.length == 0) {
					getFullModelPath0(eObj.eContainer, mappingBlock.targetModelPath, isSourceModelPath)
				} else {
					getFullModelPath0(eObj.eContainer, mappingBlock.targetModelPath + '.' + modelPath,
						isSourceModelPath)
				}

			}
		}

	}

	def static String getXMemberFeatureCallPath(XAbstractFeatureCall featureCall, String path) {

		if (featureCall instanceof XFeatureCall) {
			return featureCall + '.' + path
		} else if (featureCall instanceof XMemberFeatureCall) {
			return getXMemberFeatureCallPath((featureCall.memberCallTarget as XAbstractFeatureCall),
				featureCall.concreteSyntaxFeatureName + '.' + path)
		}

	}

	def static boolean isNotInMemberFeatureCall(XFeatureCall featureCall) {
		if (featureCall.eContainer instanceof XMemberFeatureCall) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 */
	def static boolean isTopMemberFeatureCall(XMemberFeatureCall memberFeatureCall) {
		if (memberFeatureCall.eContainer instanceof XMemberFeatureCall) {
			return false;
		} else {
			return true;
		}
	}

}
