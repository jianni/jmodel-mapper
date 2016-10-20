package com.github.jmodel.mapper.plugin.validation

import com.github.jmodel.mapper.plugin.mappingLanguage.Block
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.xbase.XExpression

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

	/**
	 * for ignoring the built-in validation, need to override this method
	 */
	override checkInnerExpressions(XExpression expr) {
		// disabled
	}

}
