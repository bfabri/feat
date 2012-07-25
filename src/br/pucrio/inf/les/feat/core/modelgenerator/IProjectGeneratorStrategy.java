package br.pucrio.inf.les.feat.core.modelgenerator;

import org.eclipse.core.resources.IProject;

import br.pucrio.inf.les.feat.core.domainmodel.Project;

/**
 * 
 * @author Bruno Fábri
 *
 */
public interface IProjectGeneratorStrategy {
	Project generate(IProject project, String version) throws ProjectGeneratorException;
}
