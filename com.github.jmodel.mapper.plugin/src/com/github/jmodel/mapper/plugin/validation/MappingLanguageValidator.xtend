package com.github.jmodel.mapper.plugin.validation

import com.github.jmodel.mapper.plugin.mappingLanguage.MappingBlock
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.xbase.XExpression

/**
 * Custom validation rules. 
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class MappingLanguageValidator extends AbstractMappingLanguageValidator {

	@Check
	def checkMappingBlock(MappingBlock mappingBlock) {
		var firstPosition = mappingBlock.sourceModelPath.indexOf('[')
		if (firstPosition > 0 && firstPosition != mappingBlock.sourceModelPath.lastIndexOf('[')) {
			error('Two array models is not allowed in a mapping block', mappingBlock, null)
		}
		firstPosition = mappingBlock.targetModelPath.indexOf('[')
		if (firstPosition > 0 && firstPosition != mappingBlock.targetModelPath.lastIndexOf('[')) {
			error('Two array models is not allowed in a mapping block', mappingBlock, null)
		}
	}

	/**
	 * for ignoring the built-in validation, need to override this method
	 */
	override checkInnerExpressions(XExpression expr) {
		// disabled
	}

}
