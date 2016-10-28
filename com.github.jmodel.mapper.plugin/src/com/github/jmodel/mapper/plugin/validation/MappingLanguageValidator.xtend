package com.github.jmodel.mapper.plugin.validation

import com.github.jmodel.mapper.plugin.mappingLanguage.Block
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.xbase.XExpression
import com.github.jmodel.mapper.plugin.mappingLanguage.Mapping
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.IPackageFragmentRoot
import org.eclipse.jdt.core.JavaModelException
import org.eclipse.xtext.xbase.XBinaryOperation
import org.eclipse.xtext.xbase.XNullLiteral
import com.github.jmodel.mapper.plugin.mappingLanguage.Body

/**
 * Custom validation rules. 
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class MappingLanguageValidator extends AbstractMappingLanguageValidator {

	@Check
	def checkBlock(Block block) {
		/*
		 * model path can't include two arrays
		 */
		var firstPosition = block.sourceModelPath.indexOf('[')
		if (firstPosition > 0 && firstPosition != block.sourceModelPath.lastIndexOf('[')) {
			error('Two array models is not allowed in a mapping block', block, null)
		}
		firstPosition = block.targetModelPath.indexOf('[')
		if (firstPosition > 0 && firstPosition != block.targetModelPath.lastIndexOf('[')) {
			error('Two array models is not allowed in a mapping block', block, null)
		}

		/*
		 * check if model path deviates from model path of parent
		 */
//		val currentMapping = Util.getBody(block).eContainer as Mapping
//		val superType = currentMapping.superType
//		val tIter = superType.eAllContents.toIterable.filter(typeof(Block)).filter(f|(!f.targetModelPath.equals('.'))).
//			filter(
//				s |
//					(
//						(Util.getFullModelPath(s, false).equals(block.targetModelPath + "[]").operator_or(s.
//							targetModelPath.substring(0, s.targetModelPath.length - 2).equals(
//								Util.getFullModelPath(block, false)))))
//			)
//		if (tIter.iterator.hasNext) {
//			error('The model path "' + block.targetModelPath + '" deviates from the model path in parent mapping',
//				block, null)
//		}

		/*
		 * check incorrect dot path
		 */
		if (block.eContainer instanceof Body) {
			if (block.sourceModelPath.equals('.').operator_or(block.targetModelPath.equals('.'))) {
				error('xxx', block, null)
			}
		}

	}

	@Check
	def checkMapping(Mapping mapping) {

		/*
		 * check name space
		 */
		val platformString = mapping.eResource.URI.toPlatformString(true)
		val myFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString))
		val proj = myFile.getProject()
		val IJavaProject javaProject = JavaCore.create(proj)
		try {
			val IPackageFragmentRoot[] packageFragmentRoot = javaProject.getAllPackageFragmentRoots();
			for (var int i = 0; i < packageFragmentRoot.length; i++) {
				val IPackageFragmentRoot thePFR = packageFragmentRoot.get(i)
				if (thePFR.getKind() == IPackageFragmentRoot.K_SOURCE && !thePFR.isArchive()) {
					val sourceFolderPath = thePFR.path.toString
					val position = platformString.indexOf(sourceFolderPath)
					if (position > -1) {
						val mappingName = platformString.substring(position + sourceFolderPath.length + 1).replace('/',
							'.')
						val expectedMappingName = mappingName.substring(0, mappingName.lastIndexOf('.'))
						if (!expectedMappingName.equals(mapping.name)) {
							error(
								'The declared mapping "' + mapping.name + '" does not match the expected mapping "' +
									expectedMappingName + '"', mapping, null)
						}
						return
					}

				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace()
			return
		}
	}

	@Check
	def checkMapping(XBinaryOperation binaryOperation) {
		if (binaryOperation.leftOperand instanceof XNullLiteral ||
			binaryOperation.rightOperand instanceof XNullLiteral) {
			val oper = binaryOperation.getConcreteSyntaxFeatureName()
			if (!oper.equals("==") && !oper.equals("!=")) {
				error('The operation "' + oper + '" is not support for null', binaryOperation, null)
			}
		}
	}

	/**
	 * important! for ignoring the built-in validation, need to override this method
	 */
	override checkInnerExpressions(XExpression expr) {
		// disabled
	}

}
