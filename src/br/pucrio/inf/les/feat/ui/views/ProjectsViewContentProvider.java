package br.pucrio.inf.les.feat.ui.views;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import br.pucrio.inf.les.feat.core.model.FeatManager;
import br.pucrio.inf.les.feat.core.model.FeatManagerEvent;
import br.pucrio.inf.les.feat.core.model.FeatManagerListener;
import br.pucrio.inf.les.feat.core.model.Feature;
import br.pucrio.inf.les.feat.core.model.Project;
import br.pucrio.inf.les.feat.core.model.Version;

public class ProjectsViewContentProvider implements ITreeContentProvider,
		FeatManagerListener {

	private TreeViewer viewer;
	private FeatManager manager;
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer) viewer;
		if (manager != null) {
			manager.removeFeatManagerListener(this);
		}
		manager = (FeatManager) newInput;
		if (manager != null) {
			manager.addFeatManagerListener(this);
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return manager.getProjects();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Project) {
			Project project = (Project) parentElement;
			return project.getVersions();
		} else if (parentElement instanceof Version) {
			Version version = (Version) parentElement;
			return version.getFeatures();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Project) {
			return element;
		}
		else if (element instanceof Version) {
			Version v = (Version) element;
			return v.getProject();
		}
		else {
			Feature f = (Feature) element;
			return f.getVersion();
		}
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Project) {
			Project project = (Project) element;
			return project.getVersions().length > 0;
		} else if (element instanceof Version) {
			Version version = (Version) element;
			return version.getFeatures().length > 0;
		}
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void featChanged(FeatManagerEvent event) {
		viewer.getTree().setRedraw(false);
		try {
			viewer.remove(event.getItemsRemoved());
			for (Project project : event.getItemsAdded()) {
				viewer.add(project, project.getVersions());
				for (Version version : project.getVersions()) {
					viewer.add(version, version.getFeatures());
				}
			}
		} finally {
			viewer.getTree().setRedraw(true);
		}
	}
}
