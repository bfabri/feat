package br.pucrio.inf.les.feat.core.modelgenerator;

import org.eclipse.core.resources.IProject;

import br.pucrio.inf.les.feat.core.domainmodel.Project;

/**
 * <p>
 * Interface comum a todos os geradores de projetos.
 * </p>
 * 
 * @author Bruno F�bri
 * @version 1.0
 *
 */
public interface IProjectGeneratorStrategy {
	/**
	 * <p>
	 * M�todo respons�vel por gerar uma vers�o de um projeto.
	 * </p>
	 * 
	 * @param project projeto que deseja gerar a vers�o
	 * @param version vers�o que ser� gerada
	 * @return {@link Project} com uma nova vers�o gerada.
	 * @throws ProjectGeneratorException quando ocorre algum erro na gera��o.
	 */
	Project generate(IProject project, String version) throws ProjectGeneratorException;
}
