package com.github.jmodel.mapper.plugin.jvmmodel


import org.eclipse.xtext.xbase.XAbstractFeatureCall
import org.eclipse.xtext.xbase.XBinaryOperation
import org.eclipse.xtext.xbase.XBooleanLiteral
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XIfExpression
import org.eclipse.xtext.xbase.XMemberFeatureCall
import org.eclipse.xtext.xbase.XNullLiteral
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import com.github.jmodel.mapper.plugin.mappingLanguage.MappingBlock
import com.github.jmodel.mapper.plugin.mappingLanguage.SingleTargetFieldMapping
import com.github.jmodel.mapper.plugin.mappingLanguage.IfTargetFieldsMapping
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPath
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathXLiteral
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathXFeatureCall
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathXParenthesizedExpression
import com.github.jmodel.mapper.plugin.mappingLanguage.SourceFieldPathSetting
import com.github.jmodel.mapper.plugin.mappingLanguage.FieldMapping
import com.github.jmodel.mapper.plugin.mappingLanguage.TargetFieldPath
import com.github.jmodel.mapper.plugin.mappingLanguage.Body

class MappingXbaseCompiler extends XbaseCompiler {

	override protected doInternalToJavaStatement(XExpression expr, ITreeAppendable it, boolean isReferenced) {
		switch expr {
			Body: {

				for (mappingBlock : expr.mappingBlocks) {
					mappingBlock.internalToJavaStatement(it, true)
				}

			}
			MappingBlock: {
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

				if (expr.eContainer instanceof MappingBlock) {

					//in a array path (not include self)
					if (Util.isInArrayPath(expr)){
							
						//always can be found
						val lastSourceArrayMappingBlock = Util.getLastArrayMappingBlock(expr, true)
						val lastSourceArrayModelPath = Util.getFullModelPath(lastSourceArrayMappingBlock, true)
						val lastTargetArrayModelPath = Util.getFullModelPath(lastSourceArrayMappingBlock, false)
						
						val l_m = it.getName(lastSourceArrayModelPath + "_" + lastTargetArrayModelPath + "_m");
						if(lastSourceArrayModelPath.equals(fullSourceModelPath)){
							strSourceModel = '''mySourceModel.getModelPathMap().get(«l_m»[0])'''
							strSourceModelPath = '''«l_m»[0]'''
						}else{
							val lastSourceArrayModelPathAfter = fullSourceModelPath.replace(lastSourceArrayModelPath, "")
							strSourceModel = '''mySourceModel.getModelPathMap().get(«l_m»[0] + "«lastSourceArrayModelPathAfter»")'''
							strSourceModelPath = '''«l_m»[0] + "«lastSourceArrayModelPathAfter»"'''
						}
					}else{
						strSourceModel = '''mySourceModel.getModelPathMap().get("«fullSourceModelPath»")'''
						strSourceModelPath = '''"«fullSourceModelPath»"'''							
					}						

					
					//in a array path (not include self)
					if (Util.isInArrayPath(expr)){
						//always can be found
						val lastTargetArrayMappingBlock = Util.getLastArrayMappingBlock(expr, false)
						if(lastTargetArrayMappingBlock==null){
							strTargetModel = '''myTargetModel.getModelPathMap().get("«fullTargetModelPath»")'''
							strTargetModelPath = '''"«fullTargetModelPath»"'''
						}else{
							val lastSourceArrayModelPath = Util.getFullModelPath(lastTargetArrayMappingBlock, true)
							val lastTargetArrayModelPath = Util.getFullModelPath(lastTargetArrayMappingBlock, false)
							val l_m = it.getName(lastSourceArrayModelPath + "_" + lastTargetArrayModelPath + "_m");
							
							if(lastTargetArrayModelPath.equals(fullTargetModelPath)){
								strTargetModel = '''myTargetModel.getModelPathMap().get(«l_m»[1])'''
								strTargetModelPath = '''«l_m»[1]'''	
								strIndex = '''Integer.valueOf(«l_m»[2])'''							
							}else{
								val lastTargetArrayModelPathAfter = fullTargetModelPath.replace(lastTargetArrayModelPath,"")
						
								strTargetModel = '''myTargetModel.getModelPathMap().get(«l_m»[1] + "«lastTargetArrayModelPathAfter»")'''
								strTargetModelPath = '''«l_m»[1] + "«lastTargetArrayModelPathAfter»"'''	
								strIndex = '''Integer.valueOf(«l_m»[2])'''		
							}		
						
						}							
					}else{
						strTargetModel = '''myTargetModel.getModelPathMap().get("«fullTargetModelPath»")'''
						strTargetModelPath = '''"«fullTargetModelPath»"'''
					}
										
					if(expr.isAppend != null){
						strIsAppend = '''true'''
					}
					
				} else {
					//root path
					strSourceModel = '''mySourceModel.getModelPathMap().get("«expr.sourceModelPath»")'''
					strSourceModelPath = '''"«expr.sourceModelPath»"'''
					strTargetModel = '''myTargetModel.getModelPathMap().get("«expr.targetModelPath»")'''
					strTargetModelPath = '''"«expr.targetModelPath»"'''					
				}


				if (Util.isArray(expr)) {
					val p = it.declareUniqueNameVariable(fullSourceModelPath + "_" + fullTargetModelPath + "_p", "p");
					newLine
					append('''java.util.function.Predicate<String> «p» = null;''')

					if (expr.filter != null) {
						val f = it.declareUniqueNameVariable(fullSourceModelPath + "_" + fullTargetModelPath + "_f", "f");

						newLine
						append('''«p» = (String «f») -> ''')

						internalToJavaExpression(expr.filter.expression, it);

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
					(fieldMapping as FieldMapping).expression.internalToJavaStatement(it, true)
				}

				for (mappingBlock : expr.mappingBlocks) {
					mappingBlock.internalToJavaStatement(it, true)
				}

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

				expr.sourceFieldPath.internalToJavaStatement(it, true)
				
				if (Util.isArrayPath(expr)){
					
					if(Util.isInAppendPath(expr) && Util.getCurrentMappingBlock(expr).isAppend==null){
						val aliasedMappingBlock = Util.getCurrentAliasedMappingBlockForTargetModelPath(expr)
						val aliasedSourceModelPath = Util.getFullModelPath(aliasedMappingBlock, true)
						val aliasedTargetModelPath = Util.getFullModelPath(aliasedMappingBlock, false)
						val a_m = it.getName(aliasedSourceModelPath + "_" + aliasedTargetModelPath + "_m")
						newLine
						append('''
							myTargetModel.getFieldPathMap().get(«a_m»[1] + ".«(expr.targetFieldName as TargetFieldPath).expression»").setValue(fieldValue); 
						''')
					}else{
						val m = it.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_m")
						newLine
						append('''
							myTargetModel.getFieldPathMap().get(«m»[1] + ".«(expr.targetFieldName as TargetFieldPath).expression»").setValue(fieldValue); 
						''')
					}						
				}else{
					newLine
					append('''
						myTargetModel.getFieldPathMap().get("«fullTargetModelPath».«(expr.targetFieldName as TargetFieldPath).expression»").setValue(fieldValue); 
					''')
				}
				
				newLine
				append('''}''')

			}
						
			IfTargetFieldsMapping: {
				newLine
				append('''{''')

				expr.sourceFieldPathIf.internalToJavaStatement(it, true)

				newLine
				append('''}''')

			}
			
			SourceFieldPath: {
				expr.expression.internalToJavaStatement(it, true)
			}
			
			SourceFieldPathXLiteral: {
				newLine
				append('''fieldValue = ''')
				switch (expr.content) {
					XStringLiteral:
						append('''"«(expr.content as XStringLiteral).value»"''')
					XNumberLiteral:
						append('''String.valueOf(«(expr.content as XNumberLiteral).value»)''')
					default:
						append('''''')
				}
				append(''';''')
			}
			
			SourceFieldPathXFeatureCall: {
				val fullSourceModelPath = Util.getFullModelPath(expr, true)
				val fullTargetModelPath = Util.getFullModelPath(expr, false)
				
				newLine
				append('''fieldValue = ''')

				if (Util.isArrayPath(expr)) {
					var String m = null;
					if(expr.isFollowRootPath!=null){
						val sourceModelPathByPath = Util.getSourceModelPathByPath(expr);
						val targetModelPathByPath = Util.getTargetModelPathByPath(expr);
						m = it.getName(sourceModelPathByPath + "_" + targetModelPathByPath + "_m")												
					}else{
						m = it.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_m")	
					}
					it.append('''mySourceModel.getFieldPathMap().get(«m»[0] + ".«expr.content»").getValue()''')
											
				} else {
					
					it.append('''mySourceModel.getFieldPathMap().get("«fullSourceModelPath».«expr.content»").getValue()''')
				}

				append(''';''')
			}
			
			SourceFieldPathXParenthesizedExpression: {
				newLine
				append('''fieldValue = ''')
				
				internalToJavaExpression(expr.content, it);
				
				append(''';''')
			}
			SourceFieldPathSetting: {
				for (singleTargetFieldMapping : expr.set) {
					singleTargetFieldMapping.internalToJavaStatement(it, true)
				}
			}
			default:
				super.doInternalToJavaStatement(expr, it, isReferenced)
		}
	}

		override protected internalToConvertedExpression(XExpression obj, ITreeAppendable it) {
			if (hasName(obj))
				append(getName(obj))

			if (obj instanceof XStringLiteral) {
				append(obj.value)
			} else if (obj instanceof XNumberLiteral) {
				append(obj.value)
			} else if (obj instanceof XNullLiteral) {
				append("null")
			} else if (obj instanceof XBooleanLiteral) {
				append(obj.isIsTrue() + "");
			} else if (obj instanceof SourceFieldPathXFeatureCall){
				val fullSourceModelPath = Util.getFullModelPath(obj, true)
				val fullTargetModelPath = Util.getFullModelPath(obj, false)
				if (Util.isArrayPath(obj)) {
					var String m = null;
					if(obj.isFollowRootPath!=null){
						val sourceModelPathByPath = Util.getSourceModelPathByPath(obj);
						val targetModelPathByPath = Util.getTargetModelPathByPath(obj);
						m = it.getName(sourceModelPathByPath + "_" + targetModelPathByPath + "_m")												
					}else{
						m = it.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_m")	
					}
					it.append('''mySourceModel.getFieldPathMap().get(«m»[0] + ".«obj.content»").getValue()''')
											
				} else {
					
					it.append('''mySourceModel.getFieldPathMap().get("«fullSourceModelPath».«obj.content»").getValue()''')
				}
			}else{
				super.internalToConvertedExpression(obj, it)
			}
		}

		override protected _toJavaStatement(XIfExpression expr, ITreeAppendable b, boolean isReferenced) {
			internalToJavaStatement(expr.getIf(), b, isReferenced);
			b.newLine().append("if (");
			internalToJavaExpression(expr.getIf(), b);
			b.append(") {").increaseIndentation();
			internalToJavaStatement(expr.getThen(), b, isReferenced);
			b.decreaseIndentation().newLine().append("}");
			if (expr.getElse() != null) {
				b.append(" else {").increaseIndentation();
				internalToJavaStatement(expr.getElse(), b, isReferenced);
				b.decreaseIndentation().newLine().append("}");
			}
		}

		override protected void _toJavaStatement(XFeatureCall expr, ITreeAppendable b, boolean isReferenced) {
			featureCalltoJavaExpression(expr, b, false)
		}

		override protected void _toJavaStatement(XAbstractFeatureCall expr, ITreeAppendable b, boolean isReferenced) {
			switch expr {
				XBinaryOperation: {
				}
				default:
					super._toJavaStatement(expr, b, isReferenced)
			}
		}

		override protected boolean isVariableDeclarationRequired(XExpression expr, ITreeAppendable b) {
			switch expr {
				XFeatureCall,
				XBinaryOperation: {
					return false
				}
				default:
					return super.isVariableDeclarationRequired(expr, b)
			}
		}

		override protected featureCalltoJavaExpression(XAbstractFeatureCall call, ITreeAppendable b,
			boolean isExpressionContext) {

			val fullSourceModelPath = Util.getFullModelPath(call, true)
			val fullTargetModelPath = Util.getFullModelPath(call, false)
			val m = b.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_m")

			switch call {
				XFeatureCall: {
					if (Util.isArrayPath(call)) {
						if (Util.isInFilter(call)) {
							b.
								append('''mySourceModel.getFieldPathMap().get(«b.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_f")» + ".«call»").getValue()''')
						} else {
							b.append('''mySourceModel.getFieldPathMap().get(«m»[0] + ".«call»").getValue()''')
						}
					} else {
						b.append('''mySourceModel.getFieldPathMap().get("«fullSourceModelPath».«call»").getValue()''')
					}
				}
				XMemberFeatureCall: {
					val xMemberFeatureCallPath = Util.getXMemberFeatureCallPath(
						call.memberCallTarget as XAbstractFeatureCall, call.concreteSyntaxFeatureName)

					if (Util.isArrayPath(call)) {
						if (Util.isInFilter(call)) {
							b.
								append('''mySourceModel.getFieldPathMap().get(«b.getName(fullSourceModelPath + "_" + fullTargetModelPath + "_f")» + ".«xMemberFeatureCallPath»").getValue()''')

						} else {
							b.
								append('''mySourceModel.getFieldPathMap().get(«m»[0] + ".«xMemberFeatureCallPath»").getValue()''')

						}
					} else {
						b.
							append('''mySourceModel.getFieldPathMap().get("«fullSourceModelPath».«xMemberFeatureCallPath»").getValue()''')
					}
				}
				XBinaryOperation: {
					b.append('''(''')
					if (call.leftOperand instanceof XAbstractFeatureCall) {
						featureCalltoJavaExpression((call.leftOperand as XAbstractFeatureCall), b, isExpressionContext)
					} else {
						internalToConvertedExpression(call.leftOperand, b)
					}
					if (call.getConcreteSyntaxFeatureName().equals("==")) {
						b.append('''.equals(''')
					} else {
						b.append(call.getConcreteSyntaxFeatureName())
					}
					if (call.rightOperand instanceof XAbstractFeatureCall) {
						featureCalltoJavaExpression((call.rightOperand as XAbstractFeatureCall), b, isExpressionContext)
					} else if (call.rightOperand instanceof XStringLiteral) {
						b.append('''"''').append((call.rightOperand as XStringLiteral).value).append('''"''')
					} else {
						internalToConvertedExpression(call.rightOperand, b)
					}
					if (call.getConcreteSyntaxFeatureName().equals("==")) {
						b.append(''')''')

					}
					b.append(''')''')
				}
				default:
					super.featureCalltoJavaExpression(call, b, isExpressionContext)
			}
		}

	}
	