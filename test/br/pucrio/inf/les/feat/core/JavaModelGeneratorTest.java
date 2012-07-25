package br.pucrio.inf.les.feat.core;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Before;
import org.junit.Test;

import br.pucrio.inf.les.feat.core.modelgenerator.JavaProjectGeneratorStrategy;
import br.pucrio.inf.les.feat.core.modelgenerator.ProjectGeneratorException;


public class JavaModelGeneratorTest {

	private IProject javaProject;
	
	@Before
	public void setUp() throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject("TEST_JAVA_GENERATOR");
		project.create(null);
		project.open(null);
 
		//set the Java nature
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
 
		//create the project
		project.setDescription(description, null);
		IJavaProject javaProject = JavaCore.create(project);
 
		//set the build path
		IClasspathEntry[] buildPath = {
				JavaCore.newSourceEntry(project.getFullPath().append("src")),
				JavaRuntime.getDefaultJREContainerEntry() };
 
		javaProject.setRawClasspath(buildPath, project.getFullPath().append(
				"bin"), null);
 
		//create folder by using resources package
		IFolder folder = project.getFolder("src");
		folder.create(true, true, null);
 
		//Add folder to Java element
		IPackageFragmentRoot srcFolder = javaProject
				.getPackageFragmentRoot(folder);
 
		//create package fragment
		IPackageFragment fragment = srcFolder.createPackageFragment(
				"br.pucrio.inf.les.test", true, null);
		
		//init code string and create compilation unit
		fragment.createCompilationUnit("Calculator.java", createJavaFile(), false, null);
		this.javaProject = javaProject.getProject();
	}
	
	@Test
	public void testGenerateJavaModel() throws ProjectGeneratorException {
		JavaProjectGeneratorStrategy generator = new JavaProjectGeneratorStrategy();
		generator.generate(javaProject, "0.0.1");
	}
	
	
	private String createJavaFile() {
		StringBuilder javaFileBuilder = new StringBuilder();
		javaFileBuilder.append("package br.pucrio.inf.les.test;\n\n");
		javaFileBuilder.append("import br.pucrio.inf.les.feat.core.Feature;\n\n");
		javaFileBuilder.append("@Feature(name = \"TST\", description = \"Feature de teste\")\n");
		javaFileBuilder.append("public class Calculator {\n\n");
		javaFileBuilder.append("@Feature(name = \"LOG\", description = \"Log do sistema\")\n");
		javaFileBuilder.append("private String log;\n\n");
		javaFileBuilder.append("@Feature(name = \"OP\", description = \"Operation\")\n");
		javaFileBuilder.append("public int sum(int a, int b) {\n");
		javaFileBuilder.append("return a + b;\n");
		javaFileBuilder.append("}\n}");
		return javaFileBuilder.toString();
	}
}
