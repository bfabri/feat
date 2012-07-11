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
import org.eclipse.jdt.core.dom.CompilationUnit;

import br.pucrio.inf.les.feat.core.Feature;

/**
 * 
 * @author Bruno Fábri
 *
 */
public class JavaModelGenerator implements IModelGenerator {
	
	private Map<ASTNode, List<Feature>> annotatedNodes;
	
	@Override
	public void generateModel(IProject project) {
		initializeNodes();
		
		try {
			IJavaProject javaProject = JavaCore.create(project);
			IPackageFragment[] packages = javaProject.getPackageFragments();
			
			for (IPackageFragment javaPackage : packages) {
				if (isSourcePackage(javaPackage)) {
					for (ICompilationUnit compilationUnit : javaPackage.getCompilationUnits()) {
						CompilationUnit compilationUnitParsered = parse(compilationUnit);
						compilationUnitParsered.accept(new AnnotatedNodeVisitor(annotatedNodes));
					}
				}
			}
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
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
	
	private void initializeNodes() {
		annotatedNodes = new HashMap<>();
	}
}
