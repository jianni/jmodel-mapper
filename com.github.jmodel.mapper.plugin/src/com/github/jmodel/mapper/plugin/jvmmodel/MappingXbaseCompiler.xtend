package com.github.jmodel.mapper.plugin.jvmmodel

import com.github.jmodel.mapper.plugin.mappingLanguage.Body
import com.github.jmodel.mapper.plugin.mappingLanguage.FieldMapping
import com.github.jmodel.mapper.plugin.mappingLanguage.IfTargetFieldsMapping
import com.github.jmodel.mapper.plugin.mappingLanguage.Block
import com.github.jmodel.mapper.plugin.mappingLanguage.SingleTargetFieldMapping
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPath
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathSetting
import com.github.jmodel.mapper.plugin.mappingLanguage.SingleSourceFieldPath
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathXLiteral
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathXParenthesizedExpression
import com.github.jmodel.mapper.plugin.mappingLanguage.TargetFieldPath
import org.eclipse.xtext.xbase.XBinaryOperation
import org.eclipse.xtext.xbase.XBooleanLiteral
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XIfExpression
import org.eclipse.xtext.xbase.XNullLiteral
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable

/**
 * The main procedure of compiling:
 * <ul>
 * <li>doInternalToJavaStatement</li> 
 * <li>_toJavaStatement</li>
 * <li>internalToJavaStatement</li>
 * <li>internalToJavaExpression</li>
 * <li>internalToConvertedExpression</li>
 * <li>_toJavaExpression</li>
 * </ul>
 */
class MappingXbaseCompiler extends XbaseCompiler {

	override protected doInternalToJavaStatement(XExpression expr, ITreeAppendable it, boolean isReferenced) {
		switch expr {
			Body: {
				for (block : expr.blocks) {
					doInternalToJavaStatement(block, it, isReferenced)
				}
			}
			Block: {
				val fullSourceModelPath = Util.getFullModelPath(expr, true)
				val fullTargetModelPath = Util.getFullModelPath(expr, false)
				val m = it.declareUniqueNameVariable(fullSourceModelPath + "_" + fullTargetModelPath + "_m", "m");

				newLine
				append('''{''')

				var String strSourceModel
				var String strSourceModelPath
				var String strTargetModel
				var String strTargetModelPath
				var String strIndex = '''0'''
				var String strIsAppend = '''false'''

				if (expr.eContainer instanceof Block) {

					// in a array path (not include self)
					if (Util.isInArrayPath(expr)) {

						// always can be found
						val lastSourceArrayBlock = Util.getLastArrayBlock(expr, true)
						val lastSourceArrayModelPath = Util.getFullModelPath(lastSourceArrayBlock, true)
						val lastTargetArrayModelPath = Util.getFullModelPath(lastSourceArrayBlock, false)

						val l_m = it.getName(lastSourceArrayModelPath + "_" + lastTargetArrayModelPath + "_m")
						if (lastSourceArrayModelPath.equals(fullSourceModelPath)) {
							strSourceModel = '''mySourceModel.getModelPathMap().get(«l_m»[0])'''
							strSourceModelPath = '''«l_m»[0]'''
						} else {
							val lastSourceArrayModelPathAfter = fullSourceModelPath.replace(lastSourceArrayModelPath,
								"")
							strSourceModel = '''mySourceModel.getModelPathMap().get(«l_m»[0] + "«lastSourceArrayModelPathAfter»")'''
							strSourceModelPath = '''«l_m»[0] + "«lastSourceArrayModelPathAfter»"'''
						}
					} else {
						strSourceModel = '''mySourceModel.getModelPathMap().get("«fullSourceModelPath»")'''
						strSourceModelPath = '''"«fullSourceModelPath»"'''
					}

					// in a array path (not include self)
					if (Util.isInArrayPath(expr)) {
						// always can be found
						val lastTargetArrayBlock = Util.getLastArrayBlock(expr, false)
						if (lastTargetArrayBlock == null) {
							strTargetModel = '''myTargetModel.getModelPathMap().get("«fullTargetModelPath»")'''
							strTargetModelPath = '''"«fullTargetModelPath»"'''
						} else {
							val lastSourceArrayModelPath = Util.getFullModelPath(lastTargetArrayBlock, true)
							val lastTargetArrayModelPath = Util.getFullModelPath(lastTargetArrayBlock, false)
							val l_m = it.getName(lastSourceArrayModelPath + "_" + lastTargetArrayModelPath + "_m")

							if (lastTargetArrayModelPath.equals(fullTargetModelPath)) {
								strTargetModel = '''myTargetModel.getModelPathMap().get(«l_m»[1])'''
								strTargetModelPath = '''«l_m»[1]'''
								strIndex = '''Integer.valueOf(«l_m»[2])'''
							} else {
								val lastTargetArrayModelPathAfter = fullTargetModelPath.replace(
									lastTargetArrayModelPath,
									"")

									strTargetModel = '''myTargetModel.getModelPathMap().get(«l_m»[1] + "«lastTargetArrayModelPathAfter»")'''
									strTargetModelPath = '''«l_m»[1] + "«lastTargetArrayModelPathAfter»"'''
									strIndex = '''Integer.valueOf(«l_m»[2])'''
								}

							}
						} else {
							strTargetModel = '''myTargetModel.getModelPathMap().get("«fullTargetModelPath»")'''
							strTargetModelPath = '''"«fullTargetModelPath»"'''
						}

						if (expr.isAppend != null) {
							strIsAppend = '''true'''
						}

					} else {
						// root model path
						strSourceModel = '''mySourceModel.getModelPathMap().get("«expr.sourceModelPath»")'''
						strSourceModelPath = '''"«expr.sourceModelPath»"'''
						strTargetModel = '''myTargetModel.getModelPathMap().get("«expr.targetModelPath»")'''
						strTargetModelPath = '''"«expr.targetModelPath»"'''
					}

					// self is array
					if (Util.isArray(expr)) {
						val p = it.declareUniqueNameVariable(fullSourceModelPath + "_" + fullTargetModelPath + "_p",
							"p")
						newLine
						append('''java.util.function.Predicate<String> «p» = null;''')

						if (expr.filter != null) {
							val f = it.declareUniqueNameVariable(fullSourceModelPath + "_" + fullTargetModelPath + "_f",
								"f")

							newLine
							append('''«p» = (String «f») -> ''')

							doInternalToJavaStatement(expr.filter.expression, it,
								isReferenced)

							append(''';''')
						}

						newLine
						append('''com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, «strSourceModel», «strTargetModel», «strSourceModelPath», «strTargetModelPath», «strIndex», «strIsAppend», «p»,''')

						newLine
						append('''(String[] «m») ->''')

						newLine
						append('''{''')

					}

					for (fieldMapping : expr.fieldMappings) {
						doInternalToJavaStatement((fieldMapping as FieldMapping).expression, it, isReferenced)
					}

					for (block : expr.blocks) {
						doInternalToJavaStatement(block, it, isReferenced)
					}

					// self is array
					if (Util.isArray(expr)) {
						newLine
						append('''});''')
					}

					newLine
					append('''}''')
				}
				SingleTargetFieldMapping: {

					val fullSourceModelPath = Util.getFullModelPath(expr, true)
					val fullTargetModelPath = Util.getFullModelPath(expr, false)

					newLine
					append('''{''')

					newLine
					append('''String fieldValue = null;''')

					doInternalToJavaStatement(expr.sourceFieldPath, it, isReferenced)

					val targetFieldPath = (expr.targetFieldPath as TargetFieldPath)

					if (Util.isArrayPath(expr)) {

						if (Util.isInAppendPath(expr) && Util.getCurrentBlock(expr).isAppend == null) {
							val aliasedBlock = Util.getCurrentAliasedBlockForTargetModelPath(expr)
							val aliasedSourceModelPath = Util.getFullModelPath(aliasedBlock, true)
							val aliasedTargetModelPath = Util.getFullModelPath(aliasedBlock, false)
							val a_m = it.getName(aliasedSourceModelPath + "_" + aliasedTargetModelPath + "_m")
							newLine
							append('''
								myTargetModel.getFieldPathMap().get(«a_m»[1] + ".«targetFieldPath.expression»").setValue(fieldValue); 
							''')
						} else {
							val m = it.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_m")
							newLine
							append('''
								myTargetModel.getFieldPathMap().get(«m»[1] + ".«targetFieldPath.expression»").setValue(fieldValue); 
							''')
						}
					} else {
						newLine
						append('''
							myTargetModel.getFieldPathMap().get("«fullTargetModelPath».«targetFieldPath.expression»").setValue(fieldValue); 
						''')
					}

					newLine
					append('''}''')

				}
				IfTargetFieldsMapping: {
					newLine
					append('''{''')
					doInternalToJavaStatement(expr.sourceFieldPathIf, it, isReferenced)
					newLine
					append('''}''')

				}
				SourceFieldPath: {
					newLine
					append('''fieldValue = ''')
					doInternalToJavaStatement(expr.expression, it, isReferenced)
					append(''';''')
				}
				SourceFieldPathXLiteral: {
					switch (expr.content) {
						XStringLiteral:
							append('''"«(expr.content as XStringLiteral).value»"''')
						XNumberLiteral:
							append('''String.valueOf(«(expr.content as XNumberLiteral).value»)''')
						default:
							append('''''')
					}
				}
				SingleSourceFieldPath: {
					val fullSourceModelPath = Util.getFullModelPath(expr, true)
					val fullTargetModelPath = Util.getFullModelPath(expr, false)

					if (Util.isSourceArrayPath(expr)) {

						if (Util.isInFilter(expr)) {
							val f = it.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_f")
							append('''mySourceModel.getFieldPathMap().get(«f» + ".«expr.content»").getValue()''')
						} else {
							var String m = null
							if (expr.absolutePath != null) {
								val sourceModelPathByPath = Util.getSourceModelPathByPath(expr)
								val targetModelPathByPath = Util.getTargetModelPathByPath(expr)
								m = getName(sourceModelPathByPath + "_" + targetModelPathByPath + "_m")
							} else {
								m = getName(fullSourceModelPath + "_" + fullTargetModelPath +
									"_m")
							}
							append('''mySourceModel.getFieldPathMap().get(«m»[0] + ".«expr.content»").getValue()''')
						}

					} else {
						append('''mySourceModel.getFieldPathMap().get("«fullSourceModelPath».«expr.content»").getValue()''')
					}
				}
				SourceFieldPathXParenthesizedExpression: {
					doInternalToJavaStatement(expr.content, it, isReferenced)
				}
				SourceFieldPathSetting: {
					for (singleTargetFieldMapping : expr.set) {
						doInternalToJavaStatement(singleTargetFieldMapping, it, isReferenced)
					}
				}
				XIfExpression: {
					newLine
					it.append("if (")
					doInternalToJavaStatement(expr.getIf(), it, isReferenced)
					it.append(") {").increaseIndentation()
					doInternalToJavaStatement(expr.getThen(), it, isReferenced)
					it.decreaseIndentation().newLine().append("}")
					if (expr.getElse() != null) {
						it.append(" else {").increaseIndentation()
						doInternalToJavaStatement(expr.getElse(), it, isReferenced)
						it.decreaseIndentation().newLine().append("}")
					}
				}
				XBinaryOperation: {
					it.append('''(''')
					doInternalToJavaStatement(expr.leftOperand, it, isReferenced)
					if (expr.getConcreteSyntaxFeatureName().equals("==")) {
						append('''.equals(''')
					} else {
						append(expr.getConcreteSyntaxFeatureName())
					}
					doInternalToJavaStatement(expr.rightOperand, it, isReferenced)
					if (expr.getConcreteSyntaxFeatureName().equals("==")) {
						append(''')''')
					}
					append(''')''')
				}
				XStringLiteral: {
					append('''"«expr.value»"''')
				}
				XNumberLiteral: {
					append(expr.value)
				}
				XNullLiteral: {
					append("null")
				}
				XBooleanLiteral: {
					append('''"«expr.isIsTrue()»"''')
				}
				default:
					super.doInternalToJavaStatement(expr, it, isReferenced)
			}
		}
	}
	