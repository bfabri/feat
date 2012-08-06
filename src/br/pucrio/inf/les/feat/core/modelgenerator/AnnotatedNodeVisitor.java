package br.pucrio.inf.les.feat.core.modelgenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import br.pucrio.inf.les.feat.core.domainmodel.Element;
import br.pucrio.inf.les.feat.core.domainmodel.FeatModifier;
import br.pucrio.inf.les.feat.core.domainmodel.FeatType;
import br.pucrio.inf.les.feat.core.domainmodel.Feature;
import br.pucrio.inf.les.feat.core.domainmodel.Field;
import br.pucrio.inf.les.feat.core.domainmodel.Method;
import br.pucrio.inf.les.feat.core.domainmodel.Project;
import br.pucrio.inf.les.feat.core.domainmodel.Type;
import br.pucrio.inf.les.feat.core.domainmodel.Version;


/**
 * <p>
 * Classe responsável por visitar os nós da árvore AST
 * gerada e extrair informações relevantes para criar
 * o projeto no formato da classe {@link Project}.
 * </p>
 * <p>
 * Implementa o padrão de projeto Visitor.
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 *
 */
public class AnnotatedNodeVisitor extends ASTVisitor {
	/**
	 * <p>
	 * Unidade de compilação que será visitada.
	 * </p>
	 */
	private final CompilationUnit compilationUnit;
	
	/**
	 * <p>
	 * Versão do projeto que será gerada.
	 * </p>
	 */
	private final Version version;
	
	/**
	 * <p>
	 * Estrutura de dados que mapeia para cada feature encontrada
	 * na unidade de compilação, os elementos que a implementam.
	 * </p>
	 */
	private Map<Feature, Set<Element>> versionFeatures;
	
	/**
	 * <p>
	 * Construtor do visitor.
	 * </p>
	 * 
	 */
	public AnnotatedNodeVisitor(final CompilationUnit compilationUnit, final Version version, final Map<Feature, Set<Element>> versionFeatures) {
		this.compilationUnit = compilationUnit;
		this.version = version;
		this.versionFeatures = versionFeatures;
	}
	
	/**
	 * <p>
	 * Visita declarações de tipos.
	 * </p>
	 */
	@Override
	public boolean visit(TypeDeclaration node) {	
		ITypeBinding binding = node.resolveBinding();
		Set<Feature> features = findFeatures(binding.getAnnotations());
		if (features.size() > 0) {
			int startLine = compilationUnit.getLineNumber(node.getStartPosition());
			Type type = createType(binding, startLine);
			updateFeatures(features, type);
		}		
		return true;
	}
	
	/**
	 * <p>
	 * Visita declarações de campos.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration node) {
		List<VariableDeclarationFragment> fragments = node.fragments();
		for (VariableDeclarationFragment fragment : fragments) {
			IVariableBinding binding = fragment.resolveBinding();
			Set<Feature> features = findFeatures(binding.getAnnotations());
			if (features.size() > 0) {
				int startLine = compilationUnit.getLineNumber(node.getStartPosition()) + 1;
				Field field = createField(binding, startLine);
				updateFeatures(features, field);
			}
		}
		return false;
	}
	
	/**
	 * <p>
	 * Visita declarações de métodos.
	 * </p>
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		IMethodBinding binding = node.resolveBinding();
		Set<Feature> features = findFeatures(binding.getAnnotations());
		if (features.size() > 0) {
			int startLine = compilationUnit.getLineNumber(node.getStartPosition()) + 1;
			Method method = createMethod(binding, startLine);
			updateFeatures(features, method);
		}
		return false;
	}
	
	/**
	 * <p>
	 * Visita declarações de enumerados.
	 * </p>
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		ITypeBinding binding = node.resolveBinding();
		Set<Feature> features = findFeatures(binding.getAnnotations());
		if (features.size() > 0) {
			int startLine = compilationUnit.getLineNumber(node.getStartPosition());
			Type type = createType(binding, startLine);
			updateFeatures(features, type);
		}		
		return true;
	}
	
	/**
	 * <p>
	 * Visita declarações de enum constants.
	 * </p>
	 */
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		int startLine = compilationUnit.getLineNumber(node.getStartPosition()) + 1;
		
		IMethodBinding methodBinding = node.resolveConstructorBinding();
		Set<Feature> methodFeatures = findFeatures(methodBinding.getAnnotations());
		if (methodFeatures.size() > 0) {
			Method method = createMethod(methodBinding, startLine);
			updateFeatures(methodFeatures, method);
		}
		
		IVariableBinding variableBinding = node.resolveVariable();
		Set<Feature> variableFeatures = findFeatures(variableBinding.getAnnotations());
		if (variableFeatures.size() > 0) {
			Field field = createField(variableBinding, startLine);
			updateFeatures(variableFeatures, field);
		}
		return false;
	}
	
	
	private Set<Feature> findFeatures(final IAnnotationBinding[] annotations) {
		Set<Feature> features = new HashSet<Feature>();		
		for (IAnnotationBinding annotationBinding : annotations) {
			ITypeBinding typeBinding = annotationBinding.getAnnotationType();
			if (typeBinding.getQualifiedName().equals("br.pucrio.inf.les.feat.core.Feature")) {
				String name = null, description = null;
				for (IMemberValuePairBinding memberValuePairBinding : annotationBinding.getAllMemberValuePairs()) {
					String attrName = memberValuePairBinding.getName();
					String attrValue = memberValuePairBinding.getValue().toString();
					if (attrName.equals("name")) {
						name = attrValue;
					}
					else if (attrName.equals("description")) {
						description = attrValue;
					}
				}
				Feature feature = new Feature(version, name, description);
				features.add(feature);
			}
		}
		return features;
	}
	
	private void updateFeatures(Set<Feature> features, Element element) {
		for (Feature feature : features) {
			element.setFeature(feature);
			if (versionFeatures.containsKey(feature)) {
				versionFeatures.get(feature).add(element);
			}
			else {
				Set<Element> elements = new HashSet<Element>();
				elements.add(element);
				versionFeatures.put(feature, elements);
			}
		}
	}
	
	private Field createField(IVariableBinding binding, int startLine) {
		String name = binding.getName();
		String fieldPackage = binding.getDeclaringClass().getPackage().getName();
		String fieldClass = binding.getDeclaringClass().getName();
		String fieldType = binding.getType().getName();
		boolean enumConstant = binding.isEnumConstant();
		FeatModifier modifier = FeatModifier.getModifier(binding.getModifiers());
		return new Field(name, fieldPackage, startLine, fieldClass, fieldType, enumConstant, modifier);
	}
	
	private Method createMethod(IMethodBinding binding, int startLine) {
		String name = binding.getName();
		String methodPackage = binding.getDeclaringClass().getPackage().getName();
		String methodClass = binding.getDeclaringClass().getName();
		ITypeBinding[] parametersType = binding.getParameterTypes();
		String[] parameters = new String[parametersType.length];
		for (int i = 0; i < parametersType.length; i++) {
			parameters[i] = parametersType[i].getName();
		}
		boolean constructor = binding.isConstructor();
		String returnType = binding.getReturnType().getName();
		FeatModifier modifier = FeatModifier.getModifier(binding.getModifiers());
		return new Method(name, methodPackage, startLine, methodClass, parameters, constructor, returnType, modifier);
	}
	
	private Type createType(ITypeBinding binding, int startLine) {
		String name = binding.getName();
		String typePackage = binding.getPackage().getName();
		FeatType type;
		if (binding.isInterface()) {
			type = FeatType.INTERFACE;
		}
		else if (binding.isEnum()) {
			type = FeatType.ENUM;
		}
		else {
			type = FeatType.CLASS;
		}
		return new Type(name, typePackage, startLine, type);
	}
}
