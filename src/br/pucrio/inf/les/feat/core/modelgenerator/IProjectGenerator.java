package br.pucrio.inf.les.feat.core.modelgenerator;

import org.eclipse.core.resources.IProject;

import br.pucrio.inf.les.feat.core.model.Project;

/**
 * 
 * @author Bruno F�bri
 *
 */
public interface IProjectGenerator {
	Project generateProject(IProject project) throws ProjectGeneratorException;
}
