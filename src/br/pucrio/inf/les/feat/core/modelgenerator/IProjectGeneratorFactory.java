package br.pucrio.inf.les.feat.core.modelgenerator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

public class IProjectGeneratorFactory {

	private static final Map<String, IProjectGenerator> GENERATORS = new HashMap<String, IProjectGenerator>() {
		private static final long serialVersionUID = 1L;

		{
			put(JavaCore.NATURE_ID, new JavaProjectGenerator());
		}

	};

	public static IProjectGenerator createProjectGeneratorFor(IProject project) throws ProjectGeneratorException {
		try {
			for (Map.Entry<String, IProjectGenerator> generator : GENERATORS
					.entrySet()) {
				if (project.hasNature(generator.getKey())) {
					return generator.getValue();
				}
			}
		} catch (CoreException e) {
			throw new ProjectGeneratorException("Ocorreu um problema ao verificar a natureza do projeto", e);
		}
		throw new ProjectGeneratorException("This kind of project isn´t supported yet.");
	}
}
