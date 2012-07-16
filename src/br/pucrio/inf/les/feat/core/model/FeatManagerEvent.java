package br.pucrio.inf.les.feat.core.model;

import java.util.EventObject;

public class FeatManagerEvent extends EventObject {
	private static final long serialVersionUID = 3697053173951102953L;
	private final Project[] added;
	private final Project[] removed;

	public FeatManagerEvent(FeatManager source, Project[] itemsAdded, Project[] itemsRemoved) {
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