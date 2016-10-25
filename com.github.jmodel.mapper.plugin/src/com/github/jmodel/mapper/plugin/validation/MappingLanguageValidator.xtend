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

/**
 * Custom validation rules. 
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class MappingLanguageValidator extends AbstractMappingLanguageValidator {

	@Check
	def checkBlock(Block block) {
		var firstPosition = block.sourceModelPath.indexOf('[')
		if (firstPosition > 0 && firstPosition != block.sourceModelPath.lastIndexOf('[')) {
			error('Two array models is not allowed in a mapping block', block, null)
		}
		firstPosition = block.targetModelPath.indexOf('[')
		if (firstPosition > 0 && firstPosition != block.targetModelPath.lastIndexOf('[')) {
			error('Two array models is not allowed in a mapping block', block, null)
		}
	}

	@Check
	def checkMapping(Mapping mapping) {
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

//		mapping.name
//		mapping.eResource.getURI
//		error('The declared mapping does not match the expected mapping', mapping, null)
//		The declared mapping "com.github.jmodel.mapper.sample1" does not match the expected package "com.github.jmodel.mapper.sample"
	/**
	 * important! for ignoring the built-in validation, need to override this method
	 */
	override checkInnerExpressions(XExpression expr) {
		// disabled
	}

}
