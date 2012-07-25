package br.pucrio.inf.les.feate.core.repository;

import java.util.EventObject;

import br.pucrio.inf.les.feat.core.domainmodel.Project;

public class ProjectChangeEvent extends EventObject {
	private static final long serialVersionUID = 3697053173951102953L;
	private final Project[] added;
	private final Project[] removed;

	public ProjectChangeEvent(ProjectRepository source, Project[] itemsAdded, Project[] itemsRemoved) {
		super(source);
		added = itemsAdded;
		removed = itemsRemoved;
	}

	public Project[] getItemsAdded() {
		return added;
	}

	public Project[] getItemsRemoved() {
		return removed;
	}
}