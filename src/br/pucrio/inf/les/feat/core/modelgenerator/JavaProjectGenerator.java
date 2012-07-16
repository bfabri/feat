package br.pucrio.inf.les.feat.core.modelgenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;

import br.pucrio.inf.les.feat.core.model.Project;

/**
 * 
 * @author Bruno Fábri
 *
 */
public class JavaProjectGenerator implements IProjectGenerator {
	
	private Project javaProject;
	private Map<ASTNode, List<Annotation>> annotatedNodes;
	
	@Override
	public Project generateProject(IProject project) throws ProjectGeneratorException {
		javaProject = null;
		annotatedNodes = new HashMap<ASTNode, List<Annotation>>();
		try {
			IJavaProject javaProject = JavaCore.create(project);
			IPackageFragment[] packages = javaProject.getPackageFragments();
			
			for (IPackageFragment javaPackage : packages) {
				if (isSourcePackage(javaPackage)) {
					for (ICompilationUnit compilationUnit : javaPackage.getCompilationUnits()) {
						CompilationUnit compilationUnitParsered = parse(compilationUnit);
						compilationUnitParsered.accept(new AnnotatedNodeVisitor(annotatedNodes));
						for (Map.Entry<ASTNode, List<Annotation>> list : annotatedNodes.entrySet()) {
							System.out.println(list.getKey());
							for (Annotation a : list.getValue()) {
								System.out.println("Feature: " + a);
							}
						}
					}
				}
			}
			
		} catch (JavaModelException e) {
			throw new ProjectGeneratorException("", e);
		}
		return javaProject;
	}
	
	private CompilationUnit parse(ICompilationUnit compilationUnit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(compilationUnit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null);
	}
	
	private boolean isSourcePackage(IPackageFragment aPackage) throws JavaModelException {
		return aPackage.getKind() == IPackageFragmentRoot.K_SOURCE;
	}
}
