package br.pucrio.inf.les.feat.ui.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import br.pucrio.inf.les.feat.core.model.FeatManager;

public class ProjectsView extends ViewPart {

	public static final String ID = "br.pucrio.inf.les.feat.views.ProjectsView";
	
	private TreeViewer viewer;
	
	public ProjectsView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI);
		viewer.setContentProvider(new ProjectsViewContentProvider());
		viewer.setLabelProvider(new ProjectsViewLabelProvider());
		viewer.setInput(FeatManager.getManager());
	}

	@Override
	public void setFocus() {
	}
}
