package br.pucrio.inf.les.feat.core.modelgenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import br.pucrio.inf.les.feat.core.domainmodel.Element;
import br.pucrio.inf.les.feat.core.domainmodel.Feature;
import br.pucrio.inf.les.feat.core.domainmodel.Project;
import br.pucrio.inf.les.feat.core.domainmodel.Version;

/**
 * <p>
 * Gerador específico de projetos Java.
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 */
public class JavaProjectGeneratorStrategy implements IProjectGeneratorStrategy {
		
	@Override
	public Project generate(IProject project, String version) throws ProjectGeneratorException {
		Project newProject = new Project(project.getName());
		Version newVersion = new Version(newProject, version, new Date());
		Map<Feature, Set<Element>> newVersionFeatures = new HashMap<Feature, Set<Element>>();
		
		try {
			IJavaProject javaProject = JavaCore.create(project);
			IPackageFragment[] packages = javaProject.getPackageFragments();
			
			for (IPackageFragment javaPackage : packages) {
				if (isSourcePackage(javaPackage)) {
					for (ICompilationUnit compilationUnit : javaPackage.getCompilationUnits()) {
						CompilationUnit compilationUnitParsered = parse(compilationUnit);
						compilationUnitParsered.accept(new AnnotatedNodeVisitor(compilationUnitParsered, newVersion, newVersionFeatures));
					}
				}
			}
			
		} catch (JavaModelException e) {
			throw new ProjectGeneratorException("", e);
		}
		
		for (Map.Entry<Feature, Set<Element>> newVersionFeature : newVersionFeatures.entrySet()) {
			Feature feature = newVersionFeature.getKey();
			for (Element element : newVersionFeature.getValue()) {
				feature.addElement(element);
			}
			newVersion.addFeature(feature);
		}
		newProject.addVersion(newVersion);
		return newProject;
	}
	
	/**
	 * <p>
	 * Criar a árvore AST de um arquivo fonte do projeto java.
	 * </p>
	 * @param compilationUnit
	 * @return árvore AST.
	 */
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
