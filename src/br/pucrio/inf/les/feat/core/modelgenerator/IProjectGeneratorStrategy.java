package br.pucrio.inf.les.feat.core.modelgenerator;

import org.eclipse.core.resources.IProject;

import br.pucrio.inf.les.feat.core.domainmodel.Project;

/**
 * <p>
 * Interface comum a todos os geradores de projetos.
 * </p>
 * 
 * @author Bruno Fábri
 * @version 1.0
 *
 */
public interface IProjectGeneratorStrategy {
	/**
	 * <p>
	 * Método responsável por gerar uma versão de um projeto.
	 * </p>
	 * 
	 * @param project projeto que deseja gerar a versão
	 * @param version versão que será gerada
	 * @return {@link Project} com uma nova versão gerada.
	 * @throws ProjectGeneratorException quando ocorre algum erro na geração.
	 */
	Project generate(IProject project, String version) throws ProjectGeneratorException;
}
