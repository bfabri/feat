package br.pucrio.inf.les.feate.core.repository;

import java.util.EventObject;

import br.pucrio.inf.les.feat.core.domainmodel.Project;

/**
 * <p>
 * Respons�vel por encapsular informa��es de uma altera��o no reposit�rio.
 * </p>
 * 
 * @author Bruno Fabri
 * @version 1.0
 *
 */
public class ProjectChangeEvent extends EventObject {
	private static final long serialVersionUID = 3697053173951102953L;
	private final Project[] added;
	private final Project[] removed;

	/**
	 * <p>
	 * Construtor do objeto do evento.
	 * </p>
	 * 
	 * @param source
	 * @param projectsAdded
	 * @param projectsRemoved
	 */
	public ProjectChangeEvent(ProjectRepository source, Project[] projectsAdded, Project[] projectsRemoved) {
		super(source);
		added = projectsAdded;
		removed = projectsRemoved;
	}

	/**
	 * <p>
	 * Obt�m os projetos adicionados.
	 * </p>
	 * 
	 * @return projetos adicionados.
	 */
	public Project[] getItemsAdded() {
		return added;
	}

	/**
	 * <p>
	 * Obt�m os projetos removidos.
	 * </p>
	 * 
	 * @return projetos removidos.
	 */
	public Project[] getItemsRemoved() {
		return removed;
	}
}