package br.pucrio.inf.les.feat.core.modelgenerator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

/**
 * <p>
 * Fábrica para criação de geradores de projetos.
 * Implementa o padrão de projeto Factory Method.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class ProjectGeneratorFactory {

	private static final Map<String, IProjectGeneratorStrategy> GENERATORS = new HashMap<String, IProjectGeneratorStrategy>() {
		private static final long serialVersionUID = 1L;

		{
			put(JavaCore.NATURE_ID, new JavaProjectGeneratorStrategy());
		}

	};

	/**
	 * <p>
	 * Método responsável por criar os geradores de projetos.
	 * </p>
	 * 
	 * @param project a partir desse objeto é possível verificar a natureza do projeto e criar o gerador correto.
	 * @return gerador do projeto.
	 * @throws ProjectGeneratorException quando a fábrica não consegue criar um gerador para um projeto de uma certa natureza.
	 */
	public static IProjectGeneratorStrategy createProjectGeneratorFor(IProject project) throws ProjectGeneratorException {
		try {
			for (Map.Entry<String, IProjectGeneratorStrategy> generator : GENERATORS
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
