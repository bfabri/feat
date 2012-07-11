package br.pucrio.inf.les.feat.core.modelgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import br.pucrio.inf.les.feat.core.Feature;


/**
 * 
 * @author Bruno Fábri
 *
 */
public class AnnotatedNodeVisitor extends ASTVisitor {

	private final Map<ASTNode, List<Feature>> annotatedNodes;
	
	/**
	 * 
	 * @param annotatedNodes
	 */
	public AnnotatedNodeVisitor(final Map<ASTNode, List<Feature>> annotatedNodes) {
		this.annotatedNodes = annotatedNodes;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		visitBodyDeclaration(node);
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(FieldDeclaration node) {
		visitBodyDeclaration(node);
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		visitBodyDeclaration(node);
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		visitBodyDeclaration(node);
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		visitBodyDeclaration(node);
		return false;
	}
	
	/**
	 * 
	 * @param node
	 */
	private void visitBodyDeclaration(final BodyDeclaration node)
    {
		List<Feature> features = new ArrayList<>();
		List<?> modifiers = node.modifiers();
		for (Object modifier : modifiers) {
			if (modifier instanceof IExtendedModifier) {
				IExtendedModifier extModifier = (IExtendedModifier) modifier;
				if (extModifier.isAnnotation()) {
					Annotation annotation = (Annotation) extModifier;
					if (annotation instanceof Feature) {
						features.add((Feature)annotation);
					}
				}
			}
		}
		annotatedNodes.put(node, features);
    }
}
