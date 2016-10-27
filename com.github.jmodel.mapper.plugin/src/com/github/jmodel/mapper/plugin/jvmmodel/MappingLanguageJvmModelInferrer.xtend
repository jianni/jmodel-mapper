package com.github.jmodel.mapper.plugin.jvmmodel

import com.github.jmodel.mapper.api.Model
import com.github.jmodel.mapper.plugin.mappingLanguage.Block
import com.github.jmodel.mapper.plugin.mappingLanguage.Mapping
import com.github.jmodel.mapper.plugin.mappingLanguage.SingleSourceFieldPath
import com.github.jmodel.mapper.plugin.mappingLanguage.TargetFieldPath
import com.google.inject.Inject
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import java.util.Map
import com.github.jmodel.mapper.plugin.mappingLanguage.Variable
import com.github.jmodel.mapper.api.IllegalException

/**
 * <p>Infers a JVM model from the source model.</p> 
 * 
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>     
 */
class MappingLanguageJvmModelInferrer extends AbstractModelInferrer {

	/**
	 * convenience API to build and initialize JVM types and their members.
	 */
	@Inject extension JvmTypesBuilder

//	@Inject com.github.jmodel.mapper.plugin.jvmmodel.MappingXbaseCompiler compiler
	/**
	 * The dispatch method {@code infer} is called for each instance of the
	 * given element's type that is contained in a resource.
	 * 
	 * @param element
	 *            the model to create one or more
	 *            {@link JvmDeclaredType declared
	 *            types} from.
	 * @param acceptor
	 *            each created
	 *            {@link JvmDeclaredType type}
	 *            without a container should be passed to the acceptor in order
	 *            get attached to the current resource. The acceptor's
	 *            {@link IJvmDeclaredTypeAcceptor#accept(org.eclipse.xtext.common.types.JvmDeclaredType)
	 *            accept(..)} method takes the constructed empty type for the
	 *            pre-indexing phase. This one is further initialized in the
	 *            indexing phase using the closure you pass to the returned
	 *            {@link IPostIndexingInitializing#initializeLater(org.eclipse.xtext.xbase.lib.Procedures.Procedure1)
	 *            initializeLater(..)}.
	 * @param isPreIndexingPhase
	 *            whether the method is called in a pre-indexing phase, i.e.
	 *            when the global index is not yet fully updated. You must not
	 *            rely on linking using the index if isPreIndexingPhase is
	 *            <code>true</code>.
	 */
	def dispatch void infer(Mapping element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {

		acceptor.accept(element.toClass(element.name)) [

			if (element.superType == null) {
				superTypes += typeRef(com.github.jmodel.mapper.api.Mapping)
			} else {
				superTypes += typeRef(element.superType.name)
			}

			members += element.toField("instance", typeRef(com.github.jmodel.mapper.api.Mapping)) [
				static = true
			]

			members += element.toConstructor() [
				visibility = JvmVisibility.PRIVATE
			]

			members += element.toMethod("getInstance", typeRef(com.github.jmodel.mapper.api.Mapping)) [
				static = true
				synchronized = true

				body = [
					append('''
						if (instance == null) {
							instance = new «element.name»();
							«element.genCommonSetting»
							«element.genOriginalPaths»
						}	
						
						return instance;
					''')
				]

			]

			members += element.toMethod("execute", typeRef(void)) [
				parameters += element.toParameter("mySourceModel", typeRef(Model))
				parameters += element.toParameter("myTargetModel", typeRef(Model))
				parameters += element.toParameter("myVariablesMap", typeRef(Map))
				annotations += annotationRef("java.lang.Override")
				exceptions += typeRef(IllegalException)
				body = element.body
			]

		]
	}

	def genCommonSetting(Mapping element) '''
		«IF element.from.name.literal== 'JSON'»								
			instance.setFromFormat(com.github.jmodel.mapper.api.FormatEnum.JSON);														
		«ELSEIF element.from.name.literal== 'XML'» 
			instance.setFromFormat(com.github.jmodel.mapper.api.FormatEnum.XML);	
		«ELSEIF element.from.name.literal== 'BEAN'» 
			instance.setFromFormat(com.github.jmodel.mapper.api.FormatEnum.BEAN);	
		«ENDIF»
		
		«IF element.to.name.literal== 'JSON'»								
			instance.setToFormat(com.github.jmodel.mapper.api.FormatEnum.JSON);														
		«ELSEIF element.to.name.literal== 'XML'» 
			instance.setToFormat(com.github.jmodel.mapper.api.FormatEnum.XML);	
		«ELSEIF element.to.name.literal== 'BEAN'» 
			instance.setToFormat(com.github.jmodel.mapper.api.FormatEnum.BEAN);	
		«ENDIF»	
		
		com.github.jmodel.mapper.api.Entity sourceRootModel = new com.github.jmodel.mapper.impl.EntityImpl();
		instance.setSourceTemplateModel(sourceRootModel);
		com.github.jmodel.mapper.api.Entity targetRootModel = new com.github.jmodel.mapper.impl.EntityImpl();
		instance.setTargetTemplateModel(targetRootModel); 	
					
	'''

	def genOriginalPaths(
		Mapping element) '''
			
			«FOR variable : element.eAllContents.toIterable.filter(typeof(Variable))»
				instance.getRawVariables().add("«Util.getVariableName(variable.expression)»");
			«ENDFOR»
						
			«FOR block : element.eAllContents.toIterable.filter(typeof(Block))»
				instance.getRawSourceFieldPaths().add("«Util.getFullModelPath(block, true)»._");
				instance.getRawTargetFieldPaths().add("«Util.getFullModelPath(block, false)»._");
			«ENDFOR»
		
		    «FOR field:element.eAllContents.toIterable.filter(typeof(SingleSourceFieldPath))»
		    	«IF field.absolutePath!=null»
		    		instance.getRawSourceFieldPaths().add("«Util.getSourceModelPathByPath(field)».«field.content»");
		    	«ELSE»
		    		instance.getRawSourceFieldPaths().add("«Util.getFullModelPath(field, true)».«field.content»");
		    	«ENDIF»		
		    «ENDFOR»		
			
			«FOR targetFieldPath : element.eAllContents.toIterable.filter(typeof(TargetFieldPath))»
				instance.getRawTargetFieldPaths().add("«Util.getFullModelPath(targetFieldPath, false)».«targetFieldPath.expression»");												
			«ENDFOR»
	'''
}
