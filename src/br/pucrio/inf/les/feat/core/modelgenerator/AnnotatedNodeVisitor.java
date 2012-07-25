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
import br.pucrio.inf.les.feat.core.domainmodel.ElementType;
import br.pucrio.inf.les.feat.core.domainmodel.Feature;
import br.pucrio.inf.les.feat.core.domainmodel.Version;


/**
 * 
 * @author Bruno Fábri
 *
 */
public class AnnotatedNodeVisitor extends ASTVisitor {
	private final CompilationUnit compilationUnit;
	private final Version version;
	private Map<Feature, Set<Element>> versionFeatures;
	
	/**
	 * 
	 * @param annotatedNodes
	 */
	public AnnotatedNodeVisitor(final CompilationUnit compilationUnit, final Version version, final Map<Feature, Set<Element>> versionFeatures) {
		this.compilationUnit = compilationUnit;
		this.version = version;
		this.versionFeatures = versionFeatures;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(TypeDeclaration node) {	
		ITypeBinding binding = node.resolveBinding();
		Set<Feature> features = findFeatures(binding.getAnnotations());
		if (features.size() > 0) {
			ElementType type = node.isInterface() ? ElementType.INTERFACE : ElementType.CLASS;
			String name = binding.getQualifiedName();
			int startLine = compilationUnit.getLineNumber(node.getStartPosition());
			int endLine = startLine + compilationUnit.getLineNumber(node.getLength());
			String location = binding.getPackage().getName();
			Element element  = new Element(name, startLine, endLine, location, type);
			updateFeatures(features, element);
		}		
		return true;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(FieldDeclaration node) {
		List<VariableDeclarationFragment> fragments = node.fragments();
		for (VariableDeclarationFragment fragment : fragments) {
			IVariableBinding binding = fragment.resolveBinding();
			Set<Feature> features = findFeatures(binding.getAnnotations());
			if (features.size() > 0) {
				String name = binding.getName();
				int startLine = compilationUnit.getLineNumber(node.getStartPosition());
				int endLine = startLine + compilationUnit.getLineNumber(node.getLength());
				String location = binding.getDeclaringClass().getQualifiedName();
				Element element = new Element(name, startLine, endLine, location, ElementType.ATTRIBUTE);
				updateFeatures(features, element);
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		IMethodBinding binding = node.resolveBinding();
		Set<Feature> features = findFeatures(binding.getAnnotations());
		if (features.size() > 0) {
			String name = binding.getName();
			int startLine = compilationUnit.getLineNumber(node.getStartPosition());
			int endLine = startLine + compilationUnit.getLineNumber(node.getLength());
			String location = binding.getDeclaringClass().getQualifiedName();
			Element element = new Element(name, startLine, endLine, location, ElementType.METHOD);
			updateFeatures(features, element);
		}
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		ITypeBinding binding = node.resolveBinding();
		Set<Feature> features = findFeatures(binding.getAnnotations());
		if (features.size() > 0) {
			String name = binding.getQualifiedName();
			int startLine = compilationUnit.getLineNumber(node.getStartPosition());
			int endLine = startLine + compilationUnit.getLineNumber(node.getLength());
			String location = binding.getPackage().getName();
			Element element  = new Element(name, startLine, endLine, location, ElementType.ENUM);
			updateFeatures(features, element);
		}		
		return true;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		IMethodBinding methodBinding = node.resolveConstructorBinding();
		Set<Feature> methodFeatures = findFeatures(methodBinding.getAnnotations());
		if (methodFeatures.size() > 0) {
			String name = methodBinding.getName();
			int startLine = compilationUnit.getLineNumber(node.getStartPosition());
			int endLine = startLine + compilationUnit.getLineNumber(node.getLength());
			String location = methodBinding.getDeclaringClass().getQualifiedName();
			Element element = new Element(name, startLine, endLine, location, ElementType.METHOD);
			updateFeatures(methodFeatures, element);
		}
		
		IVariableBinding variableBinding = node.resolveVariable();
		Set<Feature> variableFeatures = findFeatures(variableBinding.getAnnotations());
		if (variableFeatures.size() > 0) {
			String name = variableBinding.getName();
			int startLine = compilationUnit.getLineNumber(node.getStartPosition());
			int endLine = startLine + compilationUnit.getLineNumber(node.getLength());
			String location = variableBinding.getDeclaringClass().getQualifiedName();
			Element element = new Element(name, startLine, endLine, location, ElementType.ATTRIBUTE);
			updateFeatures(variableFeatures, element);
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
}
